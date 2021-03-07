package com.rmgx.asset_management.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmgx.asset_management.entity.Asset;
import com.rmgx.asset_management.entity.Category;
import com.rmgx.asset_management.entity.Employee;
import com.rmgx.asset_management.repository.AssetRepository;
import com.rmgx.asset_management.repository.EmployeeRepository;

@Service
public class AssetService {

	public enum AssignmentStatus {
		AVAILABLE("Available"), ASSIGNED("Assigned"), RECOVERED("Recovered");

		AssignmentStatus(String name) {
			this.name = name;
		}

		private String name;

		@Override
		public String toString() {
			return this.name;
		}
	}

	private AssetRepository assetRepository;
	private EmployeeRepository employeeRepository;
	private CategoryService categoryService;

	@Autowired
	public AssetService(AssetRepository assetRepository, EmployeeRepository employeeRepository,
			CategoryService categoryService) {
		this.assetRepository = assetRepository;
		this.employeeRepository = employeeRepository;
		this.categoryService = categoryService;
	}

	public Asset add(Asset asset) {
		Category category = null;
		if (!Objects.isNull(asset.getCategory()) && Objects.isNull(asset.getCategory().getId())) {
			category = categoryService.findById(asset.getCategory().getId());
		} else {
			category = categoryService.add(asset.getCategory());
		}
		asset.setAssignmentStatus(AssignmentStatus.AVAILABLE.toString());
		asset.setCategory(category);
		return assetRepository.save(asset);
	}

	public List<Asset> getAllAsset() {
		return assetRepository.findAll();
	}

	public Asset findByName(String name) {
		return assetRepository.findByName(name);
	}

	public Asset update(String name, Asset asset) {
		Asset dbAsset = assetRepository.findByName(name);
		if (!Objects.isNull(asset)) {
			asset.setAssignmentStatus(dbAsset.getAssignmentStatus());
			BeanUtils.copyProperties(asset, dbAsset);
			return assetRepository.save(dbAsset);
		}
		return null;
	}

	public Asset assign(String assetName, String empName) {

		Employee employee = employeeRepository.findByFullName(empName);
		Asset asset = assetRepository.findByName(assetName);
		if (!Objects.isNull(employee) && !Objects.isNull(asset)) {
			asset.setEmployee(employee);
			asset.setAssignmentStatus(AssignmentStatus.ASSIGNED.toString());
			return assetRepository.save(asset);
		}
		return null;
	}

	public Asset recover(String name) {
		Asset asset = assetRepository.findByName(name);
		if (!Objects.isNull(asset)) {
			asset.setEmployee(null);
			asset.setAssignmentStatus(AssignmentStatus.RECOVERED.toString());
			return assetRepository.save(asset);
		}
		return null;
	}

	public Asset delete(String name) {
		Asset asset = assetRepository.findByName(name);
		if (!Objects.isNull(asset)
				&& !asset.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.ASSIGNED.toString())) {
			assetRepository.delete(asset);
			return asset;
		}
		return null;
	}
}
