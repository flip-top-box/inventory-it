package com.it_inventory.api.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT a FROM Item a WHERE LOWER(a.description) LIKE LOWER('%' || :description || '%')")
    List<Item> findItemByDescription(@Param("description") String description);

    @Query("SELECT a FROM Item a WHERE LOWER(a.brand) LIKE LOWER('%' || :brand || '%')")
    List<Item> findItemByBrand(@Param("brand") String brand);

    @Query("SELECT a FROM Item a WHERE LOWER(a.location) LIKE LOWER('%' || :location || '%')")
    List<Item> findItemByLocation(@Param("location") String location);

    @Query("SELECT a FROM Item a WHERE LOWER(a.model) LIKE LOWER('%' || :model || '%')")
    List<Item> findItemByModel(@Param("model") String model);

    @Query("SELECT a FROM Item a WHERE a.send_email = true AND a.count < a.min_val AND a.is_asset = true")
    List<Item> findItemsToOrderAsset();

    @Query("SELECT a FROM Item a WHERE a.send_email = true AND a.count < a.min_val AND a.is_asset = false")
    List<Item> findItemsToOrder();
}
