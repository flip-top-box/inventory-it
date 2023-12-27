package com.it_inventory.api.Slack.SlackEmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;




@RestController
@RequestMapping(path = "api/v1/slack_emp")
public class EmpController {

    private final EmpService empService;

    public EmpController(EmpService empService) {this.empService = empService;}

    private static final Logger logger = LoggerFactory.getLogger(EmpController.class);

    @GetMapping("/notifications/{notifications}")
    public ResponseEntity<List<Emp>> getEmpByNotifications(@PathVariable String notifications) {
        try {
            List<Emp> employees = empService.getEmpByNotifications(notifications);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            // Log the exception instead of printing the stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
