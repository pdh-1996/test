package com.znsd.pdh.util;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoHelper {

	private static MongoClient mongoClient = null;
	
	private static MongoDatabase mgdb = null;
	

	static {
		mongoClient = new MongoClient("localhost", 27017);
		mgdb = mongoClient.getDatabase("local");
	}
	
	public static MongoDatabase getMongoDatabase() {
		return mgdb;
	} 
	
	public static MongoCollection<Document> getMongoCollection(String collectionName){
		return getMongoDatabase().getCollection(collectionName);
	}
	

}
