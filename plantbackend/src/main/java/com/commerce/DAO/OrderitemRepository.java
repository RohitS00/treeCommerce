package com.commerce.DAO;

import com.commerce.entity.Orderitem;
import com.commerce.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderitemRepository extends JpaRepository<Orderitem, Long> {
}
