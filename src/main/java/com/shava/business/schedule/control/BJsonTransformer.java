package com.shava.business.schedule.control;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.enterprise.context.ApplicationScoped;

import org.bson.BsonNumber;
import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.shava.business.schedule.entity.User;

@ApplicationScoped
public class BJsonTransformer {

	public BJsonTransformer() {
		// TODO Auto-generated constructor stub
	}
	
	public Document convertToBJson(User user){
		GsonBuilder builder = new GsonBuilder(); 
		//Adapter dateTime
		builder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			@Override
			public JsonElement serialize(LocalDateTime date, Type arg1, JsonSerializationContext context) {
				// TODO Auto-generated method stub
				return new JsonPrimitive(date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			}
		});
        Gson gson = builder.create();
        String json = gson.toJson(user);
        Document document = Document.parse(json);
        //id standard field mongoDB
        document.put("_id", user.getId());
		return document;
	}
	
	public User convertFromJson(Document json){
		Type listOfObject = new TypeToken<User>() {
		}.getType();
		// Creates the json object which will manage the information received
		GsonBuilder builder = new GsonBuilder();
		// Register an adapter to manage the date types as long values
		builder.registerTypeAdapter(LocalTime.class, new JsonDeserializer<LocalTime>() {
			public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				int hour = json.getAsJsonObject().get("hour").getAsInt();
				int minute = json.getAsJsonObject().get("minute").getAsInt();
				int second = json.getAsJsonObject().get("second").getAsInt();
				return LocalTime.of(hour, minute, second);
			}
		});
		//Adapter from dateTime
		builder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return Instant.ofEpochMilli(json.getAsJsonObject().get("$numberLong").getAsLong()).atZone(ZoneId.systemDefault()).toLocalDateTime();
			}
		});
		Gson gson = builder.create();
		User user = gson.fromJson(json.toJson(), listOfObject);
		return user;
	}

}
