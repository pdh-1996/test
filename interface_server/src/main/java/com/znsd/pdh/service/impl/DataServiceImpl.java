package com.znsd.pdh.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.znsd.pdh.exception.JsonParseException;
import com.znsd.pdh.exception.ObjectNameNotFoundException;
import com.znsd.pdh.service.IDataService;
import com.znsd.pdh.support.FindSupport;
import com.znsd.pdh.support.InsertSupport;
import com.znsd.pdh.util.CollectionStringUtil;
import com.znsd.pdh.util.JsonUtils;

@Component
public class DataServiceImpl implements IDataService {

	@Autowired
	private MongoTemplate mongoTemplate;

/*	{
		mongoTemplate = new MongoTemplate(new MongoClient("localhost", 27017), "data_test");
	}*/

	/**
	 * @param json
	 * @throws Exception
	 */
	public void save(String json) throws Exception {
		if (!JsonUtils.isJson(json))
			throw new JsonParseException();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JSON.parse(json);
		String objName = null;
		if (map != null) {
			objName = map.get(CollectionStringUtil.OBJECT_NAME).toString();
			if (objName == null)
				throw new ObjectNameNotFoundException();
		}
		InsertSupport.saveExecutor(map, mongoTemplate);
	}

	/**
	 * @return
	 */
	public String findAllDriver() throws Exception {
		DBCollection collection = getCollection(CollectionStringUtil.DRIVER_COLLECTION);
		DBCursor find = collection.find();
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		for (DBObject dbObject : find) {
			list.add((BasicDBObject) dbObject);
		}
		return JSON.serialize(list);
	}

	/**
	 * @param param
	 * @return
	 */
	public String findDriverByType(String param) throws Exception {
		DBCollection collection = getCollection(CollectionStringUtil.DRIVER_COLLECTION);
		DBCursor find = collection.find(new BasicDBObject(CollectionStringUtil.OBJECT_NAME, param));
		if (!find.hasNext())
			return null;
		return JSON.serialize(find.next());
	}

	private DBCollection getCollection(String collectionName) {
		return mongoTemplate.getCollection(collectionName);
	}

	/**
	 * 按照对象类型与指定条件查询
	 * 
	 * @param dataType
	 *            传入对象类型
	 * @param query
	 *            传入条件集合
	 * @return 返回所需json字符串
	 */
	public String find(String dataType, Map<String, Object> query) throws Exception {
		if (dataType == null || query == null)
			throw new IllegalArgumentException("parameters can't be null");
		DBCollection collection = this.getCollection(CollectionStringUtil.DATA_COLLECTION);
		DBCollection relCollection = this.getCollection(CollectionStringUtil.RELATION_COLLECTION);
		BasicDBObject basicDBObject = new BasicDBObject(CollectionStringUtil.OBJECT_NAME, dataType);
		Set<Entry<String, Object>> entrySet = query.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			basicDBObject.append(entry.getKey(), entry.getValue());
		}
		DBCursor find = collection.find(basicDBObject);
		List<BasicDBObject> mainList = FindSupport.findExcutor(dataType, collection, relCollection, find);
		return JSON.serialize(mainList);
	}

	/**
	 * 根据数据类型查找此类型所有数据，包括子类型
	 * 
	 * @param dataType
	 *            传入数据类型
	 * @return 返回所需json字符串
	 */
	public String findyAll(String dataType) throws Exception {
		DBCollection collection = this.getCollection(CollectionStringUtil.DATA_COLLECTION);
		DBCollection relCollection = this.getCollection(CollectionStringUtil.RELATION_COLLECTION);
		DBCursor find = collection.find(new BasicDBObject(CollectionStringUtil.OBJECT_NAME, dataType));
		List<BasicDBObject> mainList = FindSupport.findExcutor(dataType, collection, relCollection, find);
		return JSON.serialize(mainList);
	}

	/**
	 * 根据对象id和对象数据类型查找
	 * 
	 * @param dataType
	 *            传入数据类型
	 * @param uuid
	 *            传入uuid
	 * @return 返回所需json字符串
	 */
	public String findById(String dataType, String uuid) throws Exception {
		DBCollection collection = this.getCollection(CollectionStringUtil.DATA_COLLECTION);
		DBCollection relCollection = this.getCollection(CollectionStringUtil.RELATION_COLLECTION);
		DBCursor find = collection.find(
				new BasicDBObject(CollectionStringUtil.OBJECT_NAME, dataType).append(CollectionStringUtil.UUID, uuid));
		List<BasicDBObject> mainList = FindSupport.findExcutor(dataType, collection, relCollection, find);
		return JSON.serialize(mainList);
	}


}
