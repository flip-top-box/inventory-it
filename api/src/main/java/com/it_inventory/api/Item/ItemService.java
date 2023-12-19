package com.it_inventory.api.Item;

import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface ItemService {

    List<Item> getItems();

    Optional<Item> getItemById(Long id);

    List<Item> findItemByDescription(String description);

    List<Item> findItemByBrand(String brand);

    List<Item> findItemByLocation(String location);

    List<Item> findItemByModel(String model);

    Item addItem(Item item);

    boolean deleteItemById(Long id);

    Map<String, Object> getItemData(Long id);

    void updateItemDescription(Long id, String new_description);

    void updateItemBrand(Long id, String brand);

    void updateItemLocation(Long id, String location);

    void updateSendEmail(Long id, Boolean send_email);

    void updateMinVal(Long id, Integer min_val);

    void updateCount(Long id, Integer new_count);

    void decrementCount(Long id, Integer decNum);

    void incrementCount(Long id, Integer incNum);


}
