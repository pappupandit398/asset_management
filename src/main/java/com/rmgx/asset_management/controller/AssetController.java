package com.rmgx.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmgx.asset_management.entity.Asset;
import com.rmgx.asset_management.service.AssetService;

@RestController
@RequestMapping(path = "asset")
public class AssetController {

	private AssetService assetService;

	@Autowired
	public AssetController(AssetService assetService) {
		this.assetService = assetService;
	}

	@PostMapping()
	public Asset add(@RequestBody Asset asset) {
		return assetService.add(asset);
	}

	@GetMapping
	public List<Asset> getAllAsset() {
		return assetService.getAllAsset();
	}

	@GetMapping("{name}")
	public Asset searchByName(@PathVariable("name") String name) {
		return assetService.findByName(name);
	}

	@PutMapping("{name}")
	public Asset update(@PathVariable("name") String name, @RequestBody Asset asset) {
		return assetService.update(name, asset);
	}

	@PostMapping("assign")
	public Asset assign(@RequestParam(name = "assetName", required = true) String assetName,
			@RequestParam(name = "empName", required = true) String empName) {
		return assetService.assign(assetName, empName);
	}

	@PostMapping("recover")
	public Asset recover(@RequestParam(name = "assetName", required = true) String assetName) {
		return assetService.recover(assetName);
	}

	@DeleteMapping
	public Asset delete(@RequestParam(name = "assetName", required = true) String assetName) {
		return assetService.delete(assetName);
	}
}
