package com.hib.test;

import java.security.Provider.Service;

import org.apache.tomcat.util.http.fileupload.ThresholdingOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hib.entities.Article;
import com.hib.entities.Supplier;

@SpringBootApplication
public class Application {
	static Database database = new Database();
	
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}
	

}
