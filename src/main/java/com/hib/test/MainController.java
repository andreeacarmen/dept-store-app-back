package com.hib.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hib.entities.Article;
import com.hib.entities.Order;
import com.hib.entities.OrderArticle;
import com.hib.entities.User;

class SessionToken{
	private String sid;
	private String username;
	public SessionToken(String sid, String username) {
		super();
		this.sid = sid;
		this.username = username;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
}

class Product{
	private int id;
	private String name;
	private String supplier;
	private float price;
	private int quantity;
	public Product(int id, String name, String supplier, float price,
			int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.supplier = supplier;
		this.price = price;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

class posOrderArticle{
	private Long id;
	private int quantity;
	
	public posOrderArticle(){}
	
	public posOrderArticle(Long id, int quantity){
		this.id = id;
		this.quantity = quantity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

@RestController
public class MainController {
	private static Database database = Application.database;
	
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/user")
	User getUserByUsername(@RequestParam(value="username",defaultValue="") String username){

		return null;
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(
			value = "/login", 
			method=RequestMethod.POST,
			headers="Accept=application/json",
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes="*/*")
	public SessionToken auth(@RequestBody(required = true) UserCredentials cred,
						HttpServletResponse response){
		String userName = cred.getUsername();
		String password = "" + cred.getPassword().hashCode();
		
		Database database = Application.database;
		
		User user = database.userDao.findByName(userName);
		
		if(user == null || password.compareTo(user.getPassword()) != 0){
			return null;
		} else {
			UUID id = AuthentificationManager.addSession(user);
			AuthentificationManager.debugSessions();
			return new SessionToken(id.toString(), userName);
		}
	}
	
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value = "/query")
	public List<Article> queryItems(@RequestParam(defaultValue = "all") String query){ 
		Pattern p;
		Matcher m;
		if(query.compareTo("all") == 0){
			p = null;
		} else {
			p = Pattern.compile(".*" + query + ".*");
		}
		
		List<Article> availableArticles = new ArrayList<>();
		for (Article a : database.articleDao.findAll())
			if(p != null){
				if (a.getQuantity() > 0 && p.matcher(a.getArticleName()).matches()){
					availableArticles.add(a);
				}
			} else {
				if (a.getQuantity() > 0){
					availableArticles.add(a);
				}
			}
		return availableArticles;
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/item/{itemId}")
	public Article getItem(@PathVariable Long itemId){
		return database.articleDao.find(itemId);
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping(value = "/order",
				method=RequestMethod.POST,
				headers="Accept=application/json",
				produces=MediaType.APPLICATION_JSON_VALUE,
				consumes="*/*")
	public String orderRequest(@RequestParam(value = "sid") String sid,
			                   @RequestBody (required = true) List<posOrderArticle> posArticles){
		//check if session
		if(!AuthentificationManager.sessionsExists(sid)){
			return "{\"Result\": \"Session expired!\"}";
		}
		
		List<OrderArticle> orderedArticles = new ArrayList<>();
		float totalPrice = 0;
		for (posOrderArticle pA : posArticles){
			Article article = database.articleDao.find(pA.getId());
			if (article.getQuantity() < pA.getQuantity())
				return "{\"Result\": \"Order succeded!\"}";
			
			totalPrice += article.getPrice() * pA.getQuantity();
			article.setQuantity(article.getQuantity() - pA.getQuantity());
			database.articleDao.update(article);
			OrderArticle orderArticle = new OrderArticle(article, pA.getQuantity());
			orderedArticles.add(orderArticle);
		}
		
		
		Date date = new Date();
		User user = AuthentificationManager.getUserFromSID(sid);
		
		Order order = new Order(date, "sent", user);
		
		order.setOrderedArticles(orderedArticles);
		order.setTotalPrice(totalPrice);
		database.orderDao.save(order);
		
		return "{\"Result\": \"Order succeded!\"}";
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/orders")
	public List<Order> getAllOrders(@RequestParam(value="sid") String sid){
		//check if session
		if(!AuthentificationManager.sessionsExists(sid)){
			return null;
		}
		
		User user = AuthentificationManager.getUserFromSID(sid);
		List<Order> allOrders = database.orderDao.findAll();
		List<Order> userOrders = new ArrayList<>();
		
		for (Order aO : allOrders){
			if (aO.getUser().equals(user)){
				for(Order o : userOrders){
					if(aO.getId() == o.getId()){
						continue;
					}
				}
				userOrders.add(aO);
			}
		}
		
		return userOrders;
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/order/{orderId}")
	public Order getOrder(@PathVariable Long orderId){
		return database.orderDao.find(orderId);
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/article/{articleId}")
	public OrderArticle getOrderArticle(@PathVariable Long articleId){
		return database.orderArticleDao.find(articleId);
	}
	
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/profile")
	public String getProfile(@RequestParam(defaultValue = "") String sid){
		if(!AuthentificationManager.sessionsExists(sid)){
			return "User not authenticated or session expired!";
		}
		
		User profile = AuthentificationManager.getUserFromSID(sid);
		if(profile == null){
			return "User not authenticated or session expired!";
		}
		
		return "Name: " + profile.getFullName() + "\nPassword: " + profile.getPassword() + "\n";
	}
	
}
