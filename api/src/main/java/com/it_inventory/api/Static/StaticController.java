package com.it_inventory.api.Static;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticController {

    @RequestMapping("/")
    public String index() {
        return "index"; // This refers to 'index.html' in 'src/main/resources/templates'
    }
}
