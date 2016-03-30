package com.hib.dao;

import com.hib.entities.OrderArticle;


/**
 * Created by bgl on 29.03.2016.
 */
public class OrderArticleDao extends JPADao<OrderArticle, Long>{

    public OrderArticleDao() {
        super(OrderArticle.class);
    }
}
