package com.hib.entities;

import java.util.List;

import javax.persistence.*;
import javax.persistence.Entity;


/**
 * Created by bgl on 28.03.2016.
 */
@Entity
@Table(name="SUPPLIER")
public class Supplier implements com.hib.entities.Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1817010579247292822L;

	@Id
    @GeneratedValue
    @Column(name = "SUPPLIER_ID")
    private Long id;

    private String name;
    
    @OneToMany(mappedBy = "supplier")
    private List<Article> articles ;

    public Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    
    public String toString(){
    	return "Supplier: " + this.name;
    }
}
