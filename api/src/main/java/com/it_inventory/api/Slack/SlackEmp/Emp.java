package com.it_inventory.api.Slack.SlackEmp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="slack_emp")
public class Emp {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private String slack_id;
    private String notifications;

    public Emp() {}

    public Emp(Integer id, String name, String slack_id, String notifications) {
        this.id = id;
        this.name = name;
        this.slack_id = slack_id;
        this.notifications = notifications;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlack_id() {
        return slack_id;
    }

    public void setSlack_id(String slack_id) {
        this.slack_id = slack_id;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }
}
