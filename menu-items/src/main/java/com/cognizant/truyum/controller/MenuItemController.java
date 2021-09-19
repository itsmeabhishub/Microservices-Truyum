package com.cognizant.truyum.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.cognizant.truyum.exception.CartEmptyException;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.service.MenuItemService;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@RestController
@RequestMapping("/menu-items")
public class MenuItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuItemController.class);

	@Autowired
	MenuItemService menuItemService;
	
	@PostMapping("/add")
	public String addMenuItem(@RequestBody MenuItem menuItem) throws CartEmptyException{
		LOGGER.info("Adding Menu Items");
		menuItemService.addMenuItem(menuItem);
		return "Added Successfully";
	}
	
	@GetMapping
	public List<MenuItem> getAllMenuItems() throws CartEmptyException{
		
		LOGGER.info("Retrieving all Menu Items");
		return menuItemService.getMenuItemListAdmin();
	}
	
	@GetMapping("/{id}")
	public MenuItem getMenuItem(@PathVariable int id) throws CartEmptyException{
		
		LOGGER.info("Retrieving Menu Items Based on ID");
		return menuItemService.getMenuItem(id);
	}
	
	
	@PutMapping("/update")
	public String modifyMenuItem(@RequestBody MenuItem menuItem) throws CartEmptyException{
		
		LOGGER.info("Modifying Menu Items Based on ID");
		menuItemService.modifyMenuItem(menuItem);
		return "Updated Successfully";
	}
	
	
}
