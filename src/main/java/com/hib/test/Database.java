package com.hib.test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hib.entities.*;
import com.hib.dao.*;

public class Database {
	protected ArticleDao articleDao = new ArticleDao();
	protected SupplierDao supplierDao = new SupplierDao();
	protected OrderDao orderDao = new OrderDao();
	protected OrderArticleDao orderArticleDao = new OrderArticleDao();
	protected UserDao userDao = new UserDao();

	public Database() {
		this.init();
	}

	public void init() {
		Supplier s1 = new Supplier("sup1");
		Supplier s2 = new Supplier("sup2");

		supplierDao.save(s1);
		supplierDao.save(s2);

		Article a1 = new Article(s1, "ceapa", 1000);
		Article a2 = new Article(s1, "usturoi", 3000);
		Article a3 = new Article(s2, "maimute", 1000);

		articleDao.save(a1);
		articleDao.save(a2);
		articleDao.save(a3);
		
		User user = new User("firstuser", "john doe", "firstStore", "pwd", User.PRIVILEGED);
		userDao.save(user);

		OrderArticle orderArticle = new OrderArticle(a1, 100);
		orderArticleDao.save(orderArticle);
		
		OrderArticle orderArticle2 = new OrderArticle(a2, 100);
		orderArticleDao.save(orderArticle2);
		
		Date date = new Date();
		Order order = new Order(date, "ordsts",user);
		
		LinkedList<OrderArticle> list = new LinkedList<>();
		orderArticle.setOrder(order);
		orderArticle2.setOrder(order);
		list.add(orderArticle);
		list.add(orderArticle2);
		
		order.setOrderedArticles(list);
		
		orderDao.save(order);
		
		user.setOrder(order);
		userDao.update(user);
		
		orderArticleDao.update(orderArticle);
		orderArticleDao.update(orderArticle2);
		
		List<OrderArticle> orderArticles = orderArticleDao.findAll();
		List<Supplier> suppliers = supplierDao.findAll();
		List<Article> articles = articleDao.findAll();

	}

}
