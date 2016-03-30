package com.hib.dao;

import java.io.Serializable;
import java.util.List;

import com.hib.entities.Entity;


public interface Dao<T extends com.hib.entities.Entity, I extends Serializable > {

	List<T> findAll();

	T find(I id);

	T save(T element );

	void delete(I id);

}
