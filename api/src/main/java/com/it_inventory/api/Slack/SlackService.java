package com.it_inventory.api.Slack;

import com.it_inventory.api.Item.Item;
import com.it_inventory.api.Slack.SlackEmp.Emp;
import com.it_inventory.api.Slack.SlackEmp.EmpService;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;

import java.io.IOException;

@Service
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private final SlackConfig slackConfig;
    private final EmpService empService;


    public SlackService(SlackConfig slackConfig, EmpService empService) {

        this.slackConfig = slackConfig;
        this.empService = empService;
    }

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

    // Format the item details for inclusion in the message
    private String formatItem(Item item) {
        return String.format("Item ID: %d,\n Description: %s,\n Brand: %s,\n Location: %s,\n Model: %s,\n Minimum Value: %d,\n Count: %d\n",
                item.getId(), item.getDescription(), item.getBrand(), item.getLocation(), item.getModel(), item.getMin_val(), item.getCount());
    }
}