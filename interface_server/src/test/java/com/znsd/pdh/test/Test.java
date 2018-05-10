/*package com.znsd.pdh.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.znsd.pdh.service.impl.DataServiceImpl;

public class Test {


	@org.junit.Test
	public void test() throws Exception {
		User user = new User();
		user.setName("大萨达撒");
		user.setPassword("大萨达撒");
		user.setObjName(user.getClass().getSimpleName());
		Clazz clazz = new Clazz();
		clazz.setId(1);
		clazz.setName("大大");
		clazz.setObjName(clazz.getClass().getSimpleName());
		Grade gra = new Grade();
		gra.setSs("法法");
		gra.setName("大大");
		gra.setType("的归属感");
		gra.setObjName(gra.getClass().getSimpleName());
		clazz.setGrade(gra);
		user.setClazz(clazz);
		Student stu = new Student();
		stu.setName("都不大萨达撒的 ");
		stu.setType("打发阿发发发");
		stu.setObjName(stu.getClass().getSimpleName());
		Student stu2 = new Student();
		stu2.setName("大萨达大萨大萨达大萨大萨");
		stu2.setType("充分三份法规的规定给");
		stu2.setObjName(stu2.getClass().getSimpleName());
		Set<Student> list = new HashSet<Student>();
		list.add(stu2);
//		list.add(stu);
		user.setList(list);
		System.out.println(user);
		DataServiceImpl dataServiceImpl = new DataServiceImpl();

//		dataServiceImpl.save(JSON.serialize(user));
		dataServiceImpl.save(JSONObject.toJSONString(user));

		
		 * String json = "{objName:"+1+"}"; Map<String,Object> map =
		 * JSON.toJavaObject(JSON.parseObject(JSONObject.toJSONString(user)),Map.class);
		 * Set<Entry<String, Object>> entrySet = map.entrySet(); for (Entry<String,
		 * Object> entry : entrySet) { if(entry.getValue() instanceof Collection) {
		 * System.out.println(entry.getValue());
		 * System.out.println(JSONArray.toJavaObject(JSON.parseObject(entry.getValue().
		 * toString()), List.class)); } else if(entry.getValue() instanceof Map) {
		 * System.out.println(entry.getValue()); } System.out.println(entry.getValue());
		 * 
		 * }
		 

	}

	@org.junit.Test
	public void sss() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mgdb = mongoClient.getDatabase("data_test");

	}

	@org.junit.Test
	public void findTest() throws Exception {
		DataServiceImpl dataServiceImpl = new DataServiceImpl();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", "的归属感");
		String findDriverByType = dataServiceImpl.findyAll("User");
//		String findAllDriver = dataServiceImpl.findDriverByType("User");
		System.out.println(findDriverByType);
		// MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("localhost",
		// 27017), "data_test");
		String param = "Clazz";
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mgdb = mongoClient.getDatabase("data_test");

		MongoCollection<Document> collection = mgdb.getCollection(CollectionStringUtil.DATA_COLLECTION);
		MongoCollection<Document> relCollection = mgdb.getCollection(CollectionStringUtil.RELATION_COLLECTION);
		FindIterable<Document> find = collection.find(new BasicDBObject(CollectionStringUtil.OBJECT_NAME, param));
		List<Document> objList = new ArrayList<Document>();
		List<Document> relList = new ArrayList<Document>();
		if(find == null) return ;
		for (Document document : find) {
			FindSupport.findObjRelation(document, collection, relCollection, objList, relList);
		}
		List<String> partitionByObjName = FindSupport.partitionByObjName(objList);
		Map<String, List<Document>> typeObjMap = new HashMap<String, List<Document>>();
		for (String string : partitionByObjName) {
			typeObjMap.put(string, FindSupport.partitionByObjName(objList, string));
		}
		List<Document> mainList = typeObjMap.remove(param);
		FindSupport.setValueToMainObj(mainList, typeObjMap, relList);
		System.out.println(JSON.serialize(mainList));
	}

	
}
*/