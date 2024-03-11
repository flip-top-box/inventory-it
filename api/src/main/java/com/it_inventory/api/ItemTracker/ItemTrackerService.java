package com.it_inventory.api.ItemTracker;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ItemTrackerService {
    private final ItemTrackerRepository itemTrackerRepository;

    public ItemTrackerService(ItemTrackerRepository itemTrackerRepository) {
        this.itemTrackerRepository = itemTrackerRepository;
    }

    public Date getCalendarCal() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }

    @Async
    public void trackItemRemoved(Long itemId, Integer amountRemoved) {

        Date monthYear = getCalendarCal();

        ItemTracker tracker = itemTrackerRepository.findItemByIDMonthYear(itemId, monthYear)
                .orElseGet(() -> {
                   ItemTracker newTracker = new ItemTracker();
                   newTracker.setItemId(itemId);
                   newTracker.setMonthYear(monthYear);
                   return newTracker;
                });

        tracker.setAmountRemoved(tracker.getAmountRemoved() != null ? tracker.getAmountRemoved() + amountRemoved : amountRemoved);
        tracker.setAmountAdded(tracker.getAmountAdded() != null ? tracker.getAmountAdded() : 0);
        itemTrackerRepository.save(tracker);
    }

    @Async
    public void trackItemAdded(Long itemId, Integer amountAdded) {

        Date monthYear = getCalendarCal();

        ItemTracker tracker = itemTrackerRepository.findItemByIDMonthYear(itemId, monthYear)
                .orElseGet(() -> {
                    ItemTracker newTracker = new ItemTracker();
                    newTracker.setItemId(itemId);
                    newTracker.setMonthYear(monthYear);
                    return newTracker;
                });

        tracker.setAmountAdded(tracker.getAmountAdded() != null ? tracker.getAmountAdded() + amountAdded : amountAdded);
        tracker.setAmountRemoved(tracker.getAmountRemoved() != null ? tracker.getAmountRemoved() : 0);
        itemTrackerRepository.save(tracker);
    }

    public List<ItemTracker> findItemTrackersByMonthYear(Date monthYear) {
        return itemTrackerRepository.findByMonthAndYear(monthYear);
    }
}
