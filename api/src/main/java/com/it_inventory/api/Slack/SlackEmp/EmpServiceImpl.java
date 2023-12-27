package com.it_inventory.api.Slack.SlackEmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    private final EmpRepository empRepository;

    @Autowired
    public EmpServiceImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }


    @Override
    public List<Emp> getEmpByNotifications(String notifications) {
        // Retrieve employees with the specified notification type from the database
        return empRepository.findByNotifications(notifications);
    }
}
