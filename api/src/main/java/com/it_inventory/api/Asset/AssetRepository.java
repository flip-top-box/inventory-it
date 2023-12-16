package com.it_inventory.api.Asset;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.ResponseEntity;

public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a FROM Asset a WHERE LOWER(a.description) LIKE LOWER('%' || :description || '%')")
    List<Asset> findAssetsByDescription(@Param("description") String description);


    @Query("SELECT a FROM Asset a WHERE LOWER(a.assigned_to) LIKE LOWER(CONCAT('%', :assigned_to, '%'))")
    List<Asset> findAssetsByAssignedTo(@Param("assigned_to") String description);

    @Query("SELECT a FROM Asset a WHERE LOWER(a.created_by) LIKE LOWER(CONCAT('%', :created_by, '%'))")
    List<Asset> findAssetsCreatedBy(@Param("created_by") String created_by);

    @Query("SELECT a FROM Asset a WHERE LOWER(a.serial) = LOWER(:serial)")
    List<Asset> getAssetBySerial(@Param("serial") String serial);

    @Query("SELECT a FROM Asset a WHERE LOWER(a.status) LIKE LOWER(CONCAT('%', :status, '%'))")
    List<Asset> getAssetsByStatus(@Param("status") String status);

    @Query("SELECT a FROM Asset a WHERE LOWER(a.location) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Asset> findAssetsByLocation(@Param("location") String location);
}

