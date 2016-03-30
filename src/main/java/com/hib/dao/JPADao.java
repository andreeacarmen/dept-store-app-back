package com.hib.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.hib.entities.Entity;

public class JPADao <T extends Entity, I extends Serializable > implements Dao<T , I>{
	protected Session session;
	protected Class<T> entityClass;
	
	public JPADao(Class<T> entityClass) {
		this.entityClass = entityClass;
		this.session = getDatabaseSession();
	}
	
	Session getDatabaseSession(){
		Configuration configuration = new Configuration().configure();

        ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(configuration.getProperties());

        SessionFactory factory = configuration.buildSessionFactory(builder.buildServiceRegistry());
        
        Session session = factory.openSession();
        
		return session;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<T> findAll(){
//		this.session=getDatabaseSession();
		
		List<T> entities = session.createQuery("from "+entityClass.getSimpleName()).list();
		
		for ( T entity : entities ){
			System.out.println(entity);
		}
		
//		session.close();
		return entities;
	}

	@Override
	public T find(I id) {
		// TODO Auto-generated method stub
		return (T) session.get(this.entityClass,id);
	}

	@Override
	public Entity save(Entity element) {
//		this.session = getDatabaseSession();
		session.beginTransaction();
		session.persist(element);
		session.getTransaction().commit();
//		session.close();
		return element;
	}
	
	public Entity update(Entity element) {
//		this.session = getDatabaseSession();
		session.beginTransaction();
		session.update(element);

		session.getTransaction().commit();
//		session.close();
		return element;
	}

	@Override
	public void delete(I id) {
		// TODO Auto-generated method stub
		if ( id == null )
			return;
		
		T entity = this.find(id);
		
		if ( entity == null )
			return;
		
		this.session.delete(entity);
		
	}

}
