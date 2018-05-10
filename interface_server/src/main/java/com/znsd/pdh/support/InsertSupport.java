package com.znsd.pdh.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.znsd.pdh.util.CollectionStringUtil;

public class InsertSupport {

	/**
	 * 添加执行器
	 * 
	 * @param map
	 *            数据map
	 * @param mongoTemplate
	 *            mongo连接
	 */
	public static void saveExecutor(Map<String, Object> map, MongoTemplate mongoTemplate) {
		List<BasicDBObject> objToSaveList = new ArrayList<BasicDBObject>();
		List<BasicDBObject> relationList = new ArrayList<BasicDBObject>();
		List<BasicDBObject> fieldList = new ArrayList<BasicDBObject>();
		InsertSupport.parseJsonStr(map, objToSaveList, relationList, fieldList, mongoTemplate);
		mongoTemplate.getCollection(CollectionStringUtil.DATA_COLLECTION).insert(objToSaveList);
		if (!relationList.isEmpty()) {
			mongoTemplate.getCollection(CollectionStringUtil.RELATION_COLLECTION).insert(relationList);
		}
		if (!fieldList.isEmpty()) {
			mongoTemplate.getCollection(CollectionStringUtil.DRIVER_COLLECTION).insert(fieldList);
		}
	}

	/**
	 * 在对象数据解析过程中将数据库的元数据同步到最新
	 * 
	 * @param objName
	 *            需要修改元数据的对象类型
	 * @param filedKey
	 *            装载最新元数据字段的容器
	 * @param mongoTemplate
	 *            你懂得
	 * @return 如果元数据不存在数据库那就返回元数据字段容器，否则就更新元数据表内的数据到最新状态且返回一个空的容器
	 */
	private static  BasicDBObject parseFieldForDriver(String objName, BasicDBObject filedKey,
			MongoTemplate mongoTemplate) {
		DBCollection collection = mongoTemplate.getCollection(CollectionStringUtil.DRIVER_COLLECTION);
		DBObject findOne = collection.findOne(new BasicDBObject(CollectionStringUtil.OBJECT_NAME, objName));
		if (findOne == null)
			return filedKey;
		Set<String> set = filedKey.keySet();
		boolean bool = false;
		BasicDBObject temp = new BasicDBObject();
		for (String key : set) {
			for (String key1 : findOne.keySet()) {
				if (key.equals(key1)) {
					bool = true;
					break;
				}
			}
			if (!bool) {
				temp.append(key, filedKey.get(key));
			}
		}
		filedKey = temp;
		if (!filedKey.isEmpty()) {
			collection.findAndModify(findOne, new BasicDBObject(CollectionStringUtil.$SET, filedKey));
		}
		return filedKey;
	}

	/**
	 * 此方法用来循环解析json字符串，也就是map集合，在解析过程中顺便将元数据获取，将关系建立
	 * 
	 * @param map
	 *            需要解析的map集合
	 * @param objToSaveList
	 *            需要保存的对象集合容器
	 * @param relationList
	 *            本数据获取到的关系集合容器
	 * @param fieldList
	 *            元数据集合容器
	 * @param mongoTemplate
	 *            你懂得
	 */
	private static void parseJsonStr(Map<String, Object> map, List<BasicDBObject> objToSaveList,
			List<BasicDBObject> relationList, List<BasicDBObject> fieldList, MongoTemplate mongoTemplate) {
		String parentId = getUUID(map);
		BasicDBObject filedValue = new BasicDBObject();
		BasicDBObject filedKey = new BasicDBObject();
		String objName = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			String key = entry.getKey();
			if (value instanceof Collection) {
				Collection<?> coll = (Collection<?>) value;
				coll.forEach(obj -> {
					term(objToSaveList, relationList, parentId, obj, fieldList, mongoTemplate);
				});
			} else if (value instanceof Map) {
				term(objToSaveList, relationList, parentId, value, fieldList, mongoTemplate);
			} else {
				filedValue.append(key, value);
			}
			if (key.equalsIgnoreCase(CollectionStringUtil.OBJECT_NAME)) {
				filedKey.append(key, value);
				objName = value.toString();
			} else {
				filedKey.append(entry.getKey(), entry.getKey());
			}
		}
		BasicDBObject forDriver = parseFieldForDriver(objName, filedKey, mongoTemplate);
		if (forDriver != null && !forDriver.isEmpty() && !objIsExist(fieldList, objName)) {
			fieldList.add(filedKey);
		}
		objToSaveList.add(filedValue);
	}

	/**
	 * 此方法是用来判断对象是否存在
	 * 
	 * @param fieldList
	 *            字段list
	 * @param objName
	 *            传入对象的包名
	 * @return
	 */
	private static boolean objIsExist(List<BasicDBObject> fieldList, String objName) {
		for (BasicDBObject basicDBObject : fieldList) {
			if (basicDBObject.containsValue(objName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 此方法是用来在循环解析的时候进行关系建立
	 * 
	 * @param objToSaveList
	 *            用来保存的对象集合
	 * @param relationList
	 *            此数据存在的关系集合
	 * @param parentId
	 *            关系的父id
	 * @param value
	 *            用来解析的数据，也就是子数据
	 * @param fieldList
	 *            字段集合，此集合用来存储元数据
	 * @param mongoTemplate
	 *            你懂得
	 */
	private static void term(List<BasicDBObject> objToSaveList, List<BasicDBObject> relationList, String parentId,
			Object value, List<BasicDBObject> fieldList, MongoTemplate mongoTemplate) {
		@SuppressWarnings("unchecked")
		Map<String, Object> objMap = (Map<String, Object>) value;
		BasicDBObject relationValue = new BasicDBObject();
		relationValue.append(CollectionStringUtil.PARENT_ID, parentId);
		relationValue.append(CollectionStringUtil.CHILD_ID, getUUID(objMap));
		relationList.add(relationValue);
		parseJsonStr(objMap, objToSaveList, relationList, fieldList, mongoTemplate);
	}

	/**
	 * 此方法用来获取指定集合内的最外层uuid
	 * 
	 * @param map
	 *            需要获取uuid的map集合
	 * @return 返回uuid
	 */
	private static String getUUID(Map<String, Object> map) {
		Object uuid = map.get(CollectionStringUtil.UUID);
		if (uuid == null) {
			uuid = UUID.randomUUID();
			map.put(CollectionStringUtil.UUID, uuid.toString());
		}
		return uuid.toString();
	}

}
