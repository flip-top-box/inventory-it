package com.it_inventory.api.Scheduler;
import com.it_inventory.api.Slack.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.it_inventory.api.Item.ItemController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class ScheduledTasks {

    private final ItemController itemController;
    private final SlackService slackService;

    private Date getPreviousMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Autowired
    public ScheduledTasks(ItemController itemController, SlackService slackService) {
        this.itemController = itemController;
        this.slackService = slackService;
    }

    // Schedule the execution of getItemsToOrder every week on Monday at 6:00 AM
    @Scheduled(cron = "0 0 10 * * MON", zone = "America/Phoenix")
    public void scheduleGetItemsToOrder() {
        itemController.getItemsToOrder();
    }

    // Schedule the execution of getItemsToOrderAsset every week on Monday at 6:00 AM
    @Scheduled(cron = "0 0 10 * * MON", zone = "America/Phoenix")
    public void scheduleGetItemsToOrderAsset() {
        itemController.getItemsToOrderAsset();
    }

    @Scheduled(cron = "0 0 10 1 * ?", zone = "America/Phoenix")
    public void generateAndSendReport() {
        Date monthYear = getPreviousMonth();
        String filePath = slackService.generateCSVFile(monthYear);
        slackService.uploadFileToSlack(filePath);
    }

    //TEST TEST TEST//
//    @Scheduled(cron = "0 28 15 * * SUN", zone = "America/Phoenix")
//    public void testGenAndSendReport() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        Date monthYear = cal.getTime();
//        String filePath = slackService.generateCSVFile(monthYear);
//        slackService.uploadFileToSlack(filePath);
//    }
}

