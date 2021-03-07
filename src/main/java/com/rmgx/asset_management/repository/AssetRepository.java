package com.rmgx.asset_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmgx.asset_management.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {

	Asset findByName(String name);
}
