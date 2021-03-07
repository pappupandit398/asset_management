package com.rmgx.asset_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmgx.asset_management.entity.Category;
import com.rmgx.asset_management.service.CategoryService;

@RestController
@RequestMapping(path = "category")
public class CategoryController {

	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping()
	public Category addCategory(@RequestBody Category category) {
		return categoryService.add(category);
	}

	@PutMapping("{id}")
	public Category updateCategory(@PathVariable("id") int id, @RequestBody Category category) {
		category.setId(id);
		return categoryService.update(id, category);
	}

	@GetMapping()
	public List<Category> getAllCategory() {
		return categoryService.getAllCategory();
	}
}
