package com.cognizant.truyum.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cognizant.truyum.exception.CartEmptyException;
import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.repository.CartItemRepository;
import com.cognizant.truyum.vo.MenuItem;
import com.cognizant.truyum.vo.ResponseTemplateVO;
import java.util.Optional;


@Service
public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Cart addCartItem(Cart cart) throws CartEmptyException{
		
		int mid=cart.getMid();
		MenuItem m = restTemplate.getForObject("http://menu-items-service/menu-items/"+mid,
                MenuItem.class);
        if(m==null)
        {
        	throw (new CartEmptyException("Menu Item with id "+mid+" not found!!!!"));
        }
        else {
        	Cart c = cartItemRepository.save(cart);
        	return c;
        }
		
	}
	
	
	@Transactional
	public List<ResponseTemplateVO> getAllCartItems() throws CartEmptyException{
		
		List<ResponseTemplateVO> vo = new ArrayList<>();
		List<Cart> cartList=cartItemRepository.findAll();
		if(cartList==null)
		{
			throw (new CartEmptyException("Cart Empty!"));
		}
		else
		{
			for(Cart c: cartList)
			{
			   MenuItem m = restTemplate.getForObject("http://menu-items-service/menu-items/"+c.getMid(),
                    MenuItem.class);

               ResponseTemplateVO temp = new ResponseTemplateVO();
               temp.setCart(c);
               temp.setMenuItem(m);
               vo.add(temp);
			}
			return vo;
		}
	}

	@Transactional
	public ResponseTemplateVO getCartItem(int cartId) throws CartEmptyException {
		
		ResponseTemplateVO vo = new ResponseTemplateVO();
		Optional<Cart> cart = cartItemRepository.findById(cartId);
		Cart c = cart.get();
		if(c==null)
		    throw (new CartEmptyException("Cart Item with ID "+cartId+" is not present"));
		
		MenuItem m = restTemplate.getForObject("http://menu-items-service/menu-items/"+c.getMid(),
				                                MenuItem.class);
		
		vo.setCart(c);
		vo.setMenuItem(m);
		 
		return vo;	
		
	}

	@Transactional
	public void removeCartItem(int cid) throws CartEmptyException {
		
		int flag=0;
		List<Cart> cartList=cartItemRepository.findAll();
		for(int i=0;i<cartList.size();i++)
		{
			if(cartList.get(i).getCid()==cid)
			{
				flag=1;
				cartList.remove(i);
				break;
			}
		}
		if(flag==0)
		{
			throw (new CartEmptyException("Menu Item is not found"));
		}
		
		
	}

}