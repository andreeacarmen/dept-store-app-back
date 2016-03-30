package com.hib.entities;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by bgl on 28.03.2016.
 */
@Entity
@Table(name="ARTICLE")
public class Article implements com.hib.entities.Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name="ARTICLE_ID")
    private Long id;

    private String articleName;
    private int quantity;
    private float price = 1.5f;
    
   
	@ManyToOne
    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;

    public Article( Supplier supplier, String articleName, int quantity) {
        this.supplier = supplier;
        this.articleName = articleName;
        this.quantity = quantity;
    }

    public Long getId(){
        return  id;
    }

    public String getArticleName() {
        return articleName;
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

    public Supplier getSupplierID() {
        return supplier;
    }
    
    public String toString(){
    	return "Article " + this.articleName + " " + this.supplier + " quantity" + this.quantity;
    }
}
