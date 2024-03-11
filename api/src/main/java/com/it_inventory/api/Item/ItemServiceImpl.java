package com.it_inventory.api.Item;

import com.it_inventory.api.ItemTracker.ItemTrackerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemTrackerService itemTrackerService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemTrackerService itemTrackerService) {
        this.itemRepository = itemRepository;
        this.itemTrackerService = itemTrackerService;
    }

    ////////////////////////////////////////////////////////
    //           !!! GET METHODS !!!                     //
    ///////////////////////////////////////////////////////

    @Override
    public List<Item> getItemsToOrderAsset() {
        return itemRepository.findItemsToOrderAsset();
    }

    @Override
    public List<Item> getItemsToOrder() {
        return itemRepository.findItemsToOrder();
    }
    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }


    @Override
    public List<Item> findItemByDescription(String description) {
        return itemRepository.findItemByDescription(description);
    }

    @Override
    public List<Item> findItemByBrand(String brand) {
        return itemRepository.findItemByBrand(brand);
    }

    @Override
    public List<Item> findItemByLocation(String location) {
        return itemRepository.findItemByLocation(location);
    }

    @Override
    public List<Item> findItemByModel(String model) {
        return itemRepository.findItemByModel(model);
    }

    ////////////////////////////////////////////////////////
    //           !!! ADD METHODS !!!                     //
    ///////////////////////////////////////////////////////
    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    ////////////////////////////////////////////////////////
    //           !!! DELETE METHODS !!!                   //
    ///////////////////////////////////////////////////////
    @Override
    public boolean deleteItemById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            itemRepository.deleteById(id);
        }

        return optionalItem.isPresent();
    }

    ////////////////////////////////////////////////////////
    //           !!! PUT METHODS !!!                     //
    ///////////////////////////////////////////////////////

    //all in one get it done
    @Override
    @Transactional
    public void updateItem(Long id, Map<String, Object> updates) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            // Update only the fields present in the JSON request
            if (updates.containsKey("description")) {
                item.setDescription((String) updates.get("description"));
            }
            if (updates.containsKey("brand")) {
                item.setBrand((String) updates.get("brand"));
            }
            if (updates.containsKey("location")) {
                item.setLocation((String) updates.get("location"));
            }
            if (updates.containsKey("model")) {
                item.setModel((String) updates.get("model"));
            }
            if (updates.containsKey("send_email")) {
                item.setSend_email((Boolean) updates.get("send_email"));
            }
            if (updates.containsKey("min_val")) {
                item.setMin_val((Integer) updates.get("min_val"));
            }
            if (updates.containsKey("count")) {
                Integer currCount = item.getCount();
                Integer newCount = (Integer) updates.get("count");

                if (newCount > currCount) {
                    Integer amountAdded = newCount - currCount;
                    itemTrackerService.trackItemAdded(id, amountAdded);
                } else if (newCount < currCount) {
                    Integer amountRemoved = currCount - newCount;
                    itemTrackerService.trackItemRemoved(id, amountRemoved);
                }
                item.setCount((Integer) updates.get("count"));
            }
            if (updates.containsKey("is_asset")) {
                item.setIs_asset((Boolean) updates.get("is_asset"));
            }

            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with ID: " + id);
        }
    }

    //GET DATA FOR PUT METHODS
    @Override
    public Map<String, Object> getItemData(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();

            Map<String, Object> itemData = new HashMap<>();
            itemData.put("id", item.getId());
            itemData.put("description", item.getDescription());
            itemData.put("brand", item.getBrand());
            itemData.put("location", item.getLocation());
            itemData.put("model", item.getModel());
            itemData.put("send_email", item.getSend_email());
            itemData.put("min_val", item.getMin_val());
            itemData.put("count", item.getCount());
            return itemData;
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }
    @Override
    public void updateItemDescription(Long id, String new_description) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setDescription(new_description);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void updateItemBrand(Long id, String brand) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setBrand(brand);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void updateItemLocation(Long id, String location) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setLocation(location);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void updateSendEmail(Long id, Boolean send_email) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setSend_email(send_email);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }

    @Override
    public void updateMinVal(Long id, Integer minVal) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setMin_val(minVal);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void updateCount(Long id, Integer new_count) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setCount(new_count);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void updateIsAsset(Long id, Boolean is_asset) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setIs_asset(is_asset);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Asset not found with id: " + id);
        }
    }
    @Override
    @Transactional
    public void decrementCount(Long id, Integer decNum) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
          Item item = optionalItem.get();
          Integer current = item.getCount();
          Integer newCount = current - decNum;
          item.setCount(newCount);
          itemRepository.save(item);
          itemTrackerService.trackItemRemoved(id, decNum);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void incrementCount(Long id, Integer incNum) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            Integer current = item.getCount();
            Integer newCount = current + incNum;
            item.setCount(newCount);
            itemRepository.save(item);
            itemTrackerService.trackItemAdded(id, incNum);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }


}
