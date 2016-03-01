package com.shava.business.store.control;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Session Bean implementation class Database
 */
@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class Database {

    /**
     * Default constructor. 
     */
    public Database() {
        // TODO Auto-generated constructor stub
    }
    
    private MongoClient mongoClient;
    
    @PostConstruct
    public void init() {
    	mongoClient = new MongoClient("nosql.database.com");
    }
    
    @PreDestroy
    public void stop() {
        mongoClient.close();
    }

    @Produces
    public MongoDatabase getMongoDB(){
    	return mongoClient.getDatabase("calendarDB");
    }

}
