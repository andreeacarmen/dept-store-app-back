package com.hib.entities;

import javax.persistence.*;

/**
 * Created by bgl on 29.03.2016.
 */
@javax.persistence.Entity
@Table(name = "ORDER_ARTICLE")
//,uniqueConstraints = 
//	@UniqueConstraint(columnNames = "ORDER_ID", name = "PERSONS_PK_CONSTRAINT")
//)
public class OrderArticle implements com.hib.entities.Entity {

	@Id
    @GeneratedValue
    @Column(name = "ORDERARTICLE_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private Order order;

    public OrderArticle(Article article, int quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public String toString(){
    	return this.article + " "+this.quantity;
    }
}
