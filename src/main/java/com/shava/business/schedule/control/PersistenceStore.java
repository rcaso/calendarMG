package com.shava.business.schedule.control;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.UpdateResult;
import com.shava.business.schedule.entity.User;

/**
 * Session Bean implementation class PersistenceStore
 */
@Stateless
@LocalBean
public class PersistenceStore {
	
	@Inject
	MongoDatabase dataBase;
	
	@Inject
	JsonTransformer jsonTransformer;
	
	@Inject
	BJsonTransformer bjsonTransformer;
	
    /**
     * Default constructor. 
     */
    public PersistenceStore() {
        // TODO Auto-generated constructor stub
    }
    
    public Object getNextSequence(){
    	Document searchQuery = new Document("_id", "userid");
        Document increase = new Document("seq", 1);
        Document updateQuery = new Document("$inc", increase);
        Document result = dataBase.getCollection("counter").findOneAndUpdate(searchQuery, updateQuery);
        return result.get("seq");
    }
    
    public Document registerUser(User user){
    	MongoCollection<Document> collection= dataBase.getCollection("calendar_user");
    	user.setId(new Long(getNextSequence().toString()));
    	Document currentUser = bjsonTransformer.convertToBJson(user); 
    	collection.insertOne(currentUser);
        return currentUser;
    }
    
    public Long loginUser(String email,String password){
    	MongoCollection<Document> collection= dataBase.getCollection("calendar_user");
    	Document userId = collection.find(Filters.and(Filters.eq("email", email),Filters.eq("userPassword", password))).
    	projection(Projections.fields(Projections.include("id"))).first();
    	if(!userId.isEmpty()){
    		return new Long(userId.get("id").toString());
    	} else {
    		return null;
    	}
    }
    
    public User loadUser(Long id) throws IOException{
    	MongoCollection<Document> collection= dataBase.getCollection("calendar_user");
    	Document currentUser = collection.find(Filters.eq("id", id)).first();
    	return bjsonTransformer.convertFromJson(currentUser);
    }
    
    public long saveScheduleUser(User user){
    	MongoCollection<Document> collection= dataBase.getCollection("calendar_user");
    	UpdateResult result = collection.updateOne(Filters.eq("id", user.getId()),new Document("$set",bjsonTransformer.convertToBJson(user)));
    	return result.getMatchedCount();
    }
    
}
