package com.it_inventory.api.Item;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import jakarta.persistence.EntityNotFoundException;



@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    ////////////////////////////////////////////////////////
    //           !!! GET METHODS !!!                     //
    ///////////////////////////////////////////////////////
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
    public void decrementCount(Long id, Integer decNum) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
          Item item = optionalItem.get();
          Integer current = item.getCount();
          decNum = current - decNum;
          item.setCount(decNum);
          itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }

    @Override
    public void incrementCount(Long id, Integer incNum) {
        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            Integer current = item.getCount();
            incNum += current;
            item.setCount(incNum);
            itemRepository.save(item);
        } else {
            throw new EntityNotFoundException("Item not found with id: " + id);
        }
    }


}
