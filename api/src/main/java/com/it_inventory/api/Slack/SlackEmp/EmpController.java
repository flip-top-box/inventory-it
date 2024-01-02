package com.it_inventory.api.Slack.SlackEmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "api/v1/slack_emp")
public class EmpController {

    private final EmpService empService;

    public EmpController(EmpService empService) {this.empService = empService;}

    private static final Logger logger = LoggerFactory.getLogger(EmpController.class);

    /////////////////////////////////////////////////////
    //          !!! GET ROUTES !!!                     //
    /////////////////////////////////////////////////////

    @GetMapping
    public ResponseEntity<List<Emp>> getEmployees() {
        try {
            List<Emp> employees = empService.getEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e){
            logger.error("Error fetching items", e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

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

    /////////////////////////////////////////////////////
    //          !!! ADD ROUTES !!!                     //
    /////////////////////////////////////////////////////

    @PostMapping("/add")
    public ResponseEntity<Emp> addEmp(@RequestBody Emp emp) {
        try {
            Emp newEmp = empService.addEmp(emp);
            return new ResponseEntity<>(newEmp, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("An error occurred while trying to add new employee " + emp, e);
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    /////////////////////////////////////////////////////
    //          !!! DELETE ROUTES !!!                  //
    /////////////////////////////////////////////////////
    @DeleteMapping("/delete_by_id/{id}")
    public ResponseEntity<String> deleteEmp(@PathVariable Integer id) {
        boolean deleted = empService.deleteEmp(id);

        if (deleted) {
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
        }
    }
}
