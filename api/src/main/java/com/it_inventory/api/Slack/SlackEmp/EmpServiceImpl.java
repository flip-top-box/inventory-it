package com.it_inventory.api.Slack.SlackEmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpServiceImpl implements EmpService {

    private final EmpRepository empRepository;

    @Autowired
    public EmpServiceImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    /////////////////////////////////////////////////////
    //          !!! GET METHOD !!!                     //
    /////////////////////////////////////////////////////
    @Override
    public List<Emp> getEmployees() {
        return empRepository.findAll();
    }
    @Override
    public List<Emp> getEmpByNotifications(String notifications) {
        // Retrieve employees with the specified notification type from the database
        return empRepository.findByNotifications(notifications);
    }

    /////////////////////////////////////////////////////
    //          !!! ADD METHOD !!!                     //
    /////////////////////////////////////////////////////
    @Override
    public Emp addEmp(Emp emp) {
        return empRepository.save(emp);
    }

    ////////////////////////////////////////////////////////
    //           !!! DELETE METHODS !!!                   //
    ///////////////////////////////////////////////////////
    @Override
    public boolean deleteEmp(Integer id) {
        Optional<Emp> optionalItem = empRepository.findById(id);

        if (optionalItem.isPresent()) {
            empRepository.deleteById(id);
        }

        return optionalItem.isPresent();
    }
}
