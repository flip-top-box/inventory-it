package com.it_inventory.api.ItemTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ItemTrackerRepository extends JpaRepository<ItemTracker, Long> {

    @Query("SELECT it FROM ItemTracker it WHERE it.itemId = :itemId AND FUNCTION('YEAR', it.monthYear) = FUNCTION('YEAR', :date) AND FUNCTION('MONTH', it.monthYear) = FUNCTION('MONTH', :date)")
    Optional<ItemTracker> findItemByIDMonthYear(Long itemId, Date date);

    @Query("SELECT it FROM ItemTracker it WHERE FUNCTION('YEAR', it.monthYear) = FUNCTION('YEAR', :date) AND FUNCTION('MONTH', it.monthYear) = FUNCTION('MONTH', :date)")
    List<ItemTracker> findByMonthAndYear(@Param("date") Date monthYear);

}
