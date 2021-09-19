package com.cognizant.truyum.vo;

import com.cognizant.truyum.model.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateVO {
	
	private Cart cart;
	private MenuItem menuItem;

}
