package com.it_inventory.api.Asset;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    // You can add custom queries here if needed
}

