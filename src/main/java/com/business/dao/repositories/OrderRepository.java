package com.business.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.business.dao.entities.Orders;
import com.business.dao.entities.User;

public interface OrderRepository extends JpaRepository<Orders, Integer>
{
	List<Orders> findOrdersByUser(User user);
}
