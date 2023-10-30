package com.commerce.DAO;

import com.commerce.entity.Cart;
import com.commerce.entity.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<Cartitem, Long> {
}
