package com.hib.test;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	@RequestMapping("/query")
	public List<Article> queryItems(@RequestParam(defaultValue = "all") String query){ 
		return database.articleDao.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/item/{itemId}")
	public Article getItem(@PathVariable Long itemId){
		return database.articleDao.find(itemId);
	}
	
	@CrossOrigin(origins = "http://localhost")
	@RequestMapping("/order")
	public String orderRequest(@RequestParam(defaultValue = "") String sid,
			                   @RequestBody (required = true) String order){
		//check if session
		if(!AuthentificationManager.sessionsExists(sid)){
			return "User not authenticated or session expired!";
		}
		
		return "Order succeded!";
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
