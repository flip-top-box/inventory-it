package com.it_inventory.api.Slack;

import com.it_inventory.api.Item.Item;
import com.it_inventory.api.Item.ItemService;
import com.it_inventory.api.ItemTracker.ItemTracker;
import com.it_inventory.api.ItemTracker.ItemTrackerService;
import com.it_inventory.api.Slack.SlackEmp.Emp;
import com.it_inventory.api.Slack.SlackEmp.EmpService;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.files.FilesUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import java.io.IOException;

@Service
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private final SlackConfig slackConfig;
    private final EmpService empService;
    private final ItemService itemService;
    private final ItemTrackerService itemTrackerService;


    public SlackService(SlackConfig slackConfig, EmpService empService, ItemTrackerService itemTrackerService,
                        ItemService itemService) {

        this.slackConfig = slackConfig;
        this.empService = empService;
        this.itemService = itemService;
        this.itemTrackerService = itemTrackerService;
    }
/////////////////////////////////////////////////////////////////////////////////////////
    //////////////////      WEEKLY SLACK ORDER NOTIFICATION     /////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
    public void sendMessage(String message) {
        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(slackConfig.getBotToken())
                    .chatPostMessage(req -> req.channel(slackConfig.getChannel()).text(message));
            logger.info("Slack message sent. Response: {}", response);
        } catch (IOException | SlackApiException e) {
            logger.error("Error sending Slack message", e);
        }
    }

    public void sendSlackMessage(List<Item> items, String notifications) {
        try {
            List<Emp> employees = empService.getEmpByNotifications(notifications);

            // Construct the message including the size and details of each item
            String mentionList = employees.stream()
                    .map(employee -> "<@" + employee.getSlack_id() + ">")
                    .collect(Collectors.joining(" "));

            String message = mentionList + "\n\nITEMS TO ORDER: " + items.size() + "\n\n"
                    + items.stream()
                    .map(this::formatItem)
                    .collect(Collectors.joining("\n"));

            // Send the message once with all mentions
            this.sendMessage(message);
        } catch (Exception e) {
            logger.error("Error sending Slack message", e);
        }
    }

    private String formatItem(Item item) {
        return String.format("Item ID: %d,\n Description: %s,\n Brand: %s,\n Location: %s,\n Model: %s,\n Minimum Value: %d,\n Count: %d\n",
                item.getId(), item.getDescription(), item.getBrand(), item.getLocation(), item.getModel(), item.getMin_val(), item.getCount());
    }


/////////////////////////////////////////////////////////////////////////////////////////
    //////////////////      MONTHLY SLACK CSV REPORT     ////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////

    public String generateCSVFile(Date monthYear) {
        String tempDir = System.getProperty("java.io.tmpdir");
        String fileName = "ItemTrackerReport_" + new SimpleDateFormat("MM-yyyy").format(monthYear) + ".csv";
        String filePath = Paths.get(tempDir, fileName).toString();

        List<ItemTracker> trackers = itemTrackerService.findItemTrackersByMonthYear(monthYear);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Description,Item ID,Starting Amount,Amount Added,Amount Used,Current Amount,Month/Year\n");
            for (ItemTracker tracker : trackers) {
                Optional<Item> itemOpt = itemService.getItemById(tracker.getItemId());
                if (itemOpt.isPresent()) {
                    Item item = itemOpt.get();
                    writer.append(String.format("%s,%d,%d,%d,%d,%d,%s\n",
                            item.getDescription(),
                            item.getId(),
                            item.getCount() - tracker.getAmountAdded() + tracker.getAmountRemoved(),
                            tracker.getAmountAdded(),
                            tracker.getAmountRemoved(),
                            item.getCount(),
                            new SimpleDateFormat("MM/yyyy").format(tracker.getMonthYear())));
                }
            }
        } catch (IOException e) {
            logger.error("Error generating CSV file: ", e);
        }
        return filePath;
    }


    public void uploadFileToSlack(String filePath) {
        File file = new File(filePath);

        try {
            FilesUploadResponse response = Slack.getInstance().methods().filesUpload(req -> req
                    .token(slackConfig.getBotToken())
                    .channels(Collections.singletonList(slackConfig.getChannel()))
                    .file(file)
                    .filename(file.getName())
                    .filetype("csv")
                    .initialComment("@here Monthly Item Tracker Report")
            );
            if (response.isOk()) {
                logger.info("File uploaded successfully to Slack.");
            } else {
                logger.error("Error uploading file to Slack: " + response.getError());
            }
        } catch (IOException | SlackApiException e) {
            logger.error("Error uploading file to Slack", e);
        } finally {
            boolean deleted = file.delete();
            if (!deleted) {
                logger.warn("Could not delete temporary file: " + filePath);
            }
        }
    }
}