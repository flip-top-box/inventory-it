package com.it_inventory.api.Types;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.repository.query.Param;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="item_types")
public class Types {

    @Id
    @GeneratedValue(strategy = IDENTITY)

    private Integer type_id;

    private String type_name;

    public Integer getId() {
        return type_id;
    }

    public void setId(Integer id) {
        this.type_id = id;
    }

    public String getType() {
        return type_name;
    }

    public void setType(String type) {
        this.type_name = type;
    }
}
