package com.cognizant.truyum.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cognizant.truyum.exception.CartEmptyException;
import com.cognizant.truyum.model.MenuItem;
import com.cognizant.truyum.repository.MenuItemRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuItemService {
	
	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Transactional
	public List<MenuItem> getMenuItemListAdmin() throws CartEmptyException{
		log.info("Start");
		List<MenuItem> list = menuItemRepository.findAll();
		log.info("End");
		if(list.size()==0)
		{
			throw new CartEmptyException("No menus");
		}
		return list;
	}


	@Transactional
	public List<MenuItem> getMenuItemListCustomer() throws CartEmptyException{
		
		log.info("Start");
		List<MenuItem> list =  menuItemRepository.findByActiveAndDateOfLaunchBefore("Yes", new Date());
		log.info("End");
		if(list.size()==0)
		{
			throw new CartEmptyException("No menus");
		}
		return list;	
	}

	
	@Transactional
	public void modifyMenuItem(MenuItem menuItem) throws CartEmptyException{
	
		log.info("Start");
		List<MenuItem> list = menuItemRepository.findAll();
		int flag=0;
		for(MenuItem m : list)
		{
			if(m.getId()==menuItem.getId())
			{
				flag=1;
				break;
			}
		}
		if(flag==1) {
		MenuItem menuItemNew = menuItemRepository.getOne(menuItem.getId());
		menuItemNew.setActive(menuItem.isActive());
		menuItemNew.setCategory(menuItem.getCategory());
		menuItemNew.setDateOfLaunch(menuItem.getDateOfLaunch());
		menuItemNew.setFreeDelivery(menuItem.isFreeDelivery());
		menuItemNew.setName(menuItem.getName());
		menuItemNew.setPrice(menuItem.getPrice());
		menuItemRepository.save(menuItemNew);
		log.info("End");
		}
		else {
			throw new CartEmptyException("No menu Item found with id "+menuItem.getId());
		}
	}

	@Transactional
	public MenuItem getMenuItem(int menuItemId) throws CartEmptyException{
		
		log.info("Start");
		Optional<MenuItem> menuItem = menuItemRepository.findById(menuItemId);
		
		MenuItem m= menuItem.get();
		if(m==null)
		{
			throw new CartEmptyException("No menu Item found with id "+menuItemId);
		}
		log.info("End");
		return m;
	}
	
	public MenuItem addMenuItem(MenuItem menuItem) throws CartEmptyException
	{
		List<MenuItem> list = menuItemRepository.findAll();
		int flag=0;
		for(MenuItem m : list)
		{
			if(m.getId()==menuItem.getId())
			{
				flag=1;
				break;
			}
		}
		if(flag==1) {
			throw new CartEmptyException("Menu Item found with id "+menuItem.getId()+"\n Change the menu Item ID");
		}
		log.info(""+menuItem.getId());
		MenuItem m = menuItemRepository.save(menuItem);
		return m;
	}
	


}