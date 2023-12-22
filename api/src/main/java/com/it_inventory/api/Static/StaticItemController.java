package com.it_inventory.api.Static;

import com.it_inventory.api.Item.ItemService;
import com.it_inventory.api.Item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List; // Added import for List

@Controller
@RequestMapping("/api/v1/static/item")
public class StaticItemController {

    private final ItemService itemService; // Ensure this service is injected or declared

    // Constructor for AssetService
    public StaticItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String getItem(Model model) {
        try {
            List<Item> items = itemService.getItems();
            model.addAttribute("items", items);
            return "fragments/inventory_fragment"; // refers to 'inventory_fragment.html'
        } catch (Exception e) {
            // logger.error("Error fetching assets", e);
            return "error_fragment"; // an error fragment view
        }
    }

}