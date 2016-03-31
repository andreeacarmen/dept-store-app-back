package com.hib.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.*;
import java.util.List;

/**
 * Created by bgl on 29.03.2016.
 */
@javax.persistence.Entity
@Table(name = "ORDER_INFO")
public class Order implements com.hib.entities.Entity {

	@Id
	@GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    private Date deliveryDate;

    private String status;
    
    private float totalPrice;
    
    @OneToOne(mappedBy="order")
//	@JoinColumn(name="USER_ID")
    private User user;
    
    @OneToMany(mappedBy="order")
    private List<OrderArticle> orderedArticles ;

    public Order(Date deliveryDate, String status, User user) {
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.user = user;
    }
    
    public void createList(){
    	this.orderedArticles = new LinkedList<>();
    }
    public Long getId() {
        return id;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderArticle> getOrderedArticles() {
        return orderedArticles;
    }

    public void setOrderedArticles(List<OrderArticle> orderedArticles) {
        this.orderedArticles = orderedArticles;
    }
    
    public String toString(){
    	return "Order "+ this.status+" "+this.deliveryDate;
    }
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
