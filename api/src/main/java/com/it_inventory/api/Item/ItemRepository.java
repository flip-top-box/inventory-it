package com.it_inventory.api.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT a FROM Item a WHERE LOWER(a.description) LIKE LOWER('%' || :description || '%')")
    List<Item> findItemByDescription(@Param("description") String description);

    @Query("SELECT a FROM Item a WHERE LOWER(a.brand) LIKE LOWER('%' || :brand || '%')")
    List<Item> findItemByBrand(@Param("brand") String brand);

    @Query("SELECT a FROM Item a WHERE LOWER(a.location) LIKE LOWER('%' || :location || '%')")
    List<Item> findItemByLocation(@Param("location") String location);

    @Query("SELECT a FROM Item a WHERE LOWER(a.model) LIKE LOWER('%' || :model || '%')")
    List<Item> findItemByModel(@Param("model") String model);
}
