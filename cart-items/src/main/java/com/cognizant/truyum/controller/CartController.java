package com.cognizant.truyum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.truyum.exception.CartEmptyException;
import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.service.CartService;
import com.cognizant.truyum.vo.MenuItem;
import com.cognizant.truyum.vo.ResponseTemplateVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/carts")
public class CartController{
	
	private static Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public String addCartItem(@RequestBody Cart cart) throws CartEmptyException{
		
		LOGGER.info("Adding Menu Items");
		cartService.addCartItem(cart);
		return "Added Successfully";
	}
	
	@GetMapping("/")
	public List<ResponseTemplateVO> getAllCartItems() throws CartEmptyException {
		
		LOGGER.info("Retrieving all Cart Items");
		return cartService.getAllCartItems();
	}

	@GetMapping("/{id}")
	public ResponseTemplateVO getCartItem(@PathVariable int id) throws CartEmptyException {
		
		LOGGER.info("Retrieving Cart Items Based on ID");
		return cartService.getCartItem(id);
	}

	
	@DeleteMapping("/delete/{id}")
	public String  deleteCartItem(@PathVariable int id) throws CartEmptyException{
		
		LOGGER.info("Deleting Cart Item");
		cartService.removeCartItem(id);
		return "Deleted Successfully" ;
	}
}
