package com.cognizant.truyum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.truyum.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<Cart, Integer>{

}
