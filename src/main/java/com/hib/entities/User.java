package com.hib.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name="USER_INFO")
public class User implements com.hib.entities.Entity {
	
	public static final boolean PRIVILEGED = true;
	public static final boolean UNPRIVILEGED = false;
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;
	
	private String username;
	private String fullName;
	private String store;
	private String password;
	private boolean privileged;
	
	@OneToOne
	@JoinColumn(name="ORDER_ID")
	private Order order;
	
	public User(String username, String fullName, String store, String password, boolean privileged) {
		this.username = username;
		this.fullName = fullName;
		this.store = store;
		this.password = password.hashCode()+"";
		this.privileged = privileged;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPrivileged() {
		return privileged;
	}

	public void setPrivileged(boolean privileged) {
		this.privileged = privileged;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public String toString(){
		return "User: "+username+ " full name: "+fullName;
	}
	
}
