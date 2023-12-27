package com.it_inventory.api.Slack;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.it_inventory.api.Item.Item;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private final SlackConfig slackConfig;


    public SlackService(SlackConfig slackConfig) {
        this.slackConfig = slackConfig;
    }

    public void sendMessage(List<Item> items, String channel) {
        try {
            // Construct the message including the size and details of each item
            String message = "ITEMS TO ORDER: " + items.size() + "\n\n"
                    + items.stream()
                    .map(this::formatItem)
                    .collect(Collectors.joining("\n"));

            ChatPostMessageResponse response = Slack.getInstance().methods(slackConfig.getBotToken())
                    .chatPostMessage(req -> req.channel(channel).text(message));

            // Log the response from Slack if needed
            logger.info("Slack message sent. Response: {}", response);
        } catch (IOException | SlackApiException e) {
            // Log the exception instead of printing the stack trace
            logger.error("Error sending Slack message", e);
        }
    }

    // Format the item details for inclusion in the message
    private String formatItem(Item item) {
        return String.format("Item ID: %d,\n Description: %s,\n Brand: %s,\n Location: %s,\n Model: %s,\n Minimum Value: %d,\n Count: %d\n",
                item.getId(), item.getDescription(), item.getBrand(), item.getLocation(), item.getModel(), item.getMin_val(), item.getCount());
    }
}
