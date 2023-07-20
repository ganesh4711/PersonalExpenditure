package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Category;
import com.project.repos.CategoryRepo;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepo cr;

	@GetMapping("/get/categories")
	public List<Category> getCategories() {
		return cr.findAll();

	}
}
