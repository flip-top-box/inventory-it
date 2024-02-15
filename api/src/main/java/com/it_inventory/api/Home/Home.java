package com.it_inventory.api.Home;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(path = "/")
public class Home {

    @GetMapping
    public String home() {
        return "OK! beanstalk deployment status OK";
    }
}
