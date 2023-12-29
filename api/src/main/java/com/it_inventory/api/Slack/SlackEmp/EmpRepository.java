package com.it_inventory.api.Slack.SlackEmp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpRepository extends JpaRepository<Emp, Integer> {
    List<Emp> findByNotifications(String notificationType);
}
