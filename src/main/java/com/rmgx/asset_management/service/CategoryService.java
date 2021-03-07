package com.rmgx.asset_management.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmgx.asset_management.entity.Category;
import com.rmgx.asset_management.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category add(Category category) {
		Category dbcategory = categoryRepository.findByName(category.getName());
		if (!Objects.isNull(dbcategory))
			return dbcategory;
		return categoryRepository.save(category);
	}

	public Category update(int id, Category category) {
		Optional<Category> dbCategory = categoryRepository.findById(id);
		if (dbCategory.isPresent()) {
			BeanUtils.copyProperties(category, dbCategory.get());
			return categoryRepository.save(dbCategory.get());
		}
		return null;
	}

	public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}

	public Category findById(int id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent())
			return category.get();
		return null;
	}
}
