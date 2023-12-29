package com.it_inventory.api.Slack.SlackEmp;

import java.util.List;
//import java.util.Optional;
//import java.util.Map;

public interface EmpService {
    List<Emp> getEmpByNotifications(String notifications);

    Emp addEmp(Emp emp);

    List<Emp> getEmployees();

    boolean deleteEmp(Integer id);
}
