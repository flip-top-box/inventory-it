package Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.it_inventory.api.Item.ItemController;

@Service
public class ScheduledTasks {

    private final ItemController itemController;

    @Autowired
    public ScheduledTasks(ItemController itemController) {
        this.itemController = itemController;
    }

    // Schedule the execution of getItemsToOrder every week on Monday at 6:00 AM
    @Scheduled(cron = "0 0 6 * * MON")
    public void scheduleGetItemsToOrder() {
        itemController.getItemsToOrder();
    }

    // Schedule the execution of getItemsToOrderAsset every week on Monday at 6:00 AM
    @Scheduled(cron = "0 0 6 * * MON")
    public void scheduleGetItemsToOrderAsset() {
        itemController.getItemsToOrderAsset();
    }
}

