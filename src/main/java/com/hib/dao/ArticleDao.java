package com.hib.dao;

import com.hib.entities.Article;

public class ArticleDao extends JPADao<Article, Long>{

	public ArticleDao() {
		super(Article.class);
	}
	
	public Article findByName(String articleName){
		return (Article) session
		.createQuery("from Article where articleName = :articleName")
		.setParameter("articleName", articleName).uniqueResult();
	}
	
}
