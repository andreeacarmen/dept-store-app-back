package com.hib.dao;

import com.hib.entities.*;

public class UserDao extends JPADao<User, Long>{

    public UserDao() {
		super(User.class);
	}
    
    public User findByName(String userName){
		return (User) session
		.createQuery("from User where userName = :userName")
		.setParameter("userName", userName).uniqueResult();
	}
    

}
