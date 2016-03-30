package com.hib.dao;


import com.hib.entities.Supplier;

public class SupplierDao extends JPADao<Supplier, Long>{

	public SupplierDao() {
		super(Supplier.class);
	}

	Supplier findByName(String supplierName){
		return (Supplier) session
		.createQuery("from Supplier where supplierName = :supplierName")
		.setParameter("supplierName", supplierName).uniqueResult();
	}
	
 }
