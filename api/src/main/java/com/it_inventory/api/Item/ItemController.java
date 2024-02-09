package com.it_inventory.api.Item;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.it_inventory.api.Slack.SlackService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping(path = "api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final SlackService slackService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService, SlackService slackService) {
        this.itemService = itemService;
        this.slackService = slackService;
    }

    /////////////////////////////////////////////////////
    //          !!! GET ROUTES !!!                     //
    /////////////////////////////////////////////////////
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        try {
            List<Item> items = itemService.getItems();
            return ResponseEntity.ok(items);
        } catch (Exception e){
            logger.error("Error fetching items", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        try {
            Optional<Item> item = itemService.getItemById(id);
            return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("An error occurred while fetching item by ID", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_by_description/{description}")
    public ResponseEntity<List<Item>> getItemByDescription(@PathVariable String description) {
        try {
            List<Item> items = itemService.findItemByDescription(description);
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(items);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching items by description", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_brand/{brand}")
    public ResponseEntity<List<Item>> getItemByBrand(@PathVariable String brand)  {
        try {
            List<Item> items = itemService.findItemByBrand(brand);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("An error occurred while fetching items by brand", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_location/{location}")
    public ResponseEntity<List<Item>> getItemByLocation(@PathVariable String location) {
        try{
            List<Item> items = itemService.findItemByLocation(location);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("An error occurred while fetching item by location", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get_by_model/{model}")
    public ResponseEntity<List<Item>> getItemByModel(@PathVariable String model) {
        try {
            List<Item> items = itemService.findItemByModel(model);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            logger.error("An error occurred while trying to fetch item by model", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/items_to_order_asset")
    public ResponseEntity<List<Item>> getItemsToOrderAsset() {
        try {
            List<Item> itemsToOrder = itemService.getItemsToOrderAsset();
            slackService.sendSlackMessage(itemsToOrder, "asset");
            return ResponseEntity.ok(itemsToOrder);
        } catch (Exception e) {
            logger.error("Error fetching items to order", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/items_to_order")
    public ResponseEntity<List<Item>> getItemsToOrder() {
        try {
            List<Item> itemsToOrder = itemService.getItemsToOrder();
            slackService.sendSlackMessage(itemsToOrder, "item");
            return ResponseEntity.ok(itemsToOrder);
        } catch (Exception e) {
            logger.error("Error fetching items to order", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }



    /////////////////////////////////////////////////////
    //          !!! ADD ROUTES !!!                     //
    /////////////////////////////////////////////////////

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        try {
            Item newItem = itemService.addItem(item);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while trying to add new item " + item, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /////////////////////////////////////////////////////
    //          !!! DELETE ROUTES !!!                  //
    /////////////////////////////////////////////////////

    @DeleteMapping("/delete_by_id/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        boolean deleted = itemService.deleteItemById(id);

        if (deleted) {
            return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

    ////////////////////////////////////////////////////////
    //           !!! PUT METHODS !!!                     //
    ///////////////////////////////////////////////////////

    //all in one get it done
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAsset(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        try {
            itemService.updateItem(id, updates);
            return ResponseEntity.ok("Item updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating Item: ", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/update_description")
    public ResponseEntity<Map<String, Object>> updateItemDescription(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newDescription = requestBody.get("new_description");
            itemService.updateItemDescription(id, newDescription);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item description", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_brand")
    public ResponseEntity<Map<String, Object>> updateItemBrand(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newBrand = requestBody.get("new_brand");
            itemService.updateItemBrand(id, newBrand);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item brand", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_location")
    public ResponseEntity<Map<String, Object>> updateItemLocation(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        try {
            String newLocation = requestBody.get("new_location");
            itemService.updateItemLocation(id, newLocation);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item location", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_send_email")
    public ResponseEntity<Map<String, Object>> updateSendEmail(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> requestBody) {
        try {
            Boolean newSendEmail = requestBody.get("new_send_email");
            itemService.updateSendEmail(id, newSendEmail);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating send_email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/update_min_val")
    public ResponseEntity<Map<String, Object>> updateMinVal(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer newMinVal = requestBody.get("new_min_val");
            itemService.updateMinVal(id, newMinVal);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item min_val", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_count")
    public ResponseEntity<Map<String, Object>> updateCount(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer newCount = requestBody.get("new_count");
            itemService.updateCount(id, newCount);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item count", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PutMapping("/{id}/update_is_asset")
    public ResponseEntity<Map<String, Object>> updateIsAsset(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> requestBody) {
        try {
            Boolean newIsAsset = requestBody.get("new_is_asset");
            itemService.updateIsAsset(id, newIsAsset);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating is_asset", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/decrement_count")
    public ResponseEntity<Map<String, Object>> decrementCount(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer decCount = requestBody.get("dec_count");
            itemService.decrementCount(id, decCount);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item count", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error, count cant be less than 0"));
        }
    }

    @PutMapping("/{id}/increment_count")
    public ResponseEntity<Map<String, Object>> incrementCount(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer incCount = requestBody.get("inc_count");
            itemService.incrementCount(id, incCount);
            Map<String, Object> itemData = itemService.getItemData(id);
            return ResponseEntity.ok(itemData);
        } catch (EntityNotFoundException e) {
            logger.warn("Item not found with id: {}", id);
            return ResponseEntity.status(NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating item count", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }


}
