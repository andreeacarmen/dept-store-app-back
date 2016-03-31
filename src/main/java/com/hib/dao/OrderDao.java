package com.hib.dao;

import com.hib.entities.Order;

import java.util.List;

/**
 * Created by bgl on 29.03.2016.
 */
public class OrderDao extends JPADao<Order, Long> {
	
    public OrderDao() {
        super(Order.class);
    }

    List<Order> findAllOrders(){
        List<Order> ordersList = (List<Order>)this.findAll();
        return ordersList;
    }
}
