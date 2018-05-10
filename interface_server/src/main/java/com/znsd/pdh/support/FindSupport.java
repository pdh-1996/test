package com.znsd.pdh.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.znsd.pdh.util.CollectionStringUtil;

public class FindSupport {

	/*
	 * public static List<BasicDBObject> findExcutor(String param,
	 * MongoCollection<BasicDBObject> collection, MongoCollection<BasicDBObject>
	 * relCollection, FindIterable<BasicDBObject> find) { List<BasicDBObject>
	 * objList = new ArrayList<BasicDBObject>(); List<BasicDBObject> relList = new
	 * ArrayList<BasicDBObject>(); if(find == null) return null; for (BasicDBObject
	 * BasicDBObject : find) { findObjRelation(BasicDBObject, collection,
	 * relCollection, objList, relList); } List<String> partitionByObjName =
	 * partitionByObjName(objList); Map<String, List<BasicDBObject>> typeObjMap =
	 * new HashMap<String, List<BasicDBObject>>(); for (String string :
	 * partitionByObjName) { typeObjMap.put(string, partitionByObjName(objList,
	 * string)); } List<BasicDBObject> mainList = typeObjMap.remove(param);
	 * setValueToMainObj(mainList, typeObjMap, relList); return mainList; }
	 * 
	 * private static void setValueToMainObj(List<BasicDBObject> mainList,
	 * Map<String, List<BasicDBObject>> typeObjMap, List<BasicDBObject> relList) {
	 * Set<Entry<String, List<BasicDBObject>>> entrySet = typeObjMap.entrySet();
	 * List<BasicDBObject> valueList; String objName = null; List<BasicDBObject>
	 * value ; for (BasicDBObject mainObj : mainList) { String parentId =
	 * mainObj.get(CollectionStringUtil.UUID).toString(); for (Entry<String,
	 * List<BasicDBObject>> entry : entrySet) { valueList = new
	 * ArrayList<BasicDBObject>(); objName = entry.getKey(); value =
	 * entry.getValue(); for (BasicDBObject doc : value) { if (isMatter(parentId,
	 * doc.get(CollectionStringUtil.UUID).toString(), relList)) {
	 * setValueToMainObj(value, typeObjMap, relList); valueList.add(doc); } } Object
	 * obj = isCollection(valueList); if(obj != null) mainObj.append(objName, obj);
	 * } } }
	 * 
	 * private static Object isCollection(List<BasicDBObject> valueList) { if
	 * (valueList.size() == 0) return null; return valueList.size() > 1 ? valueList
	 * : valueList.get(0); }
	 * 
	 * private static boolean isMatter(String parentId, String childId,
	 * List<BasicDBObject> relList) { for (BasicDBObject BasicDBObject : relList) {
	 * if (parentId.equals(BasicDBObject.get(CollectionStringUtil.PARENT_ID)) &&
	 * childId.equals(BasicDBObject.get(CollectionStringUtil.CHILD_ID))) {
	 * relList.remove(BasicDBObject); return true; } } return false; }
	 * 
	 * private static List<BasicDBObject> partitionByObjName(List<BasicDBObject>
	 * objList, String objName) { List<BasicDBObject> list = new
	 * ArrayList<BasicDBObject>(); for (BasicDBObject BasicDBObject : objList) { if
	 * (BasicDBObject.get(CollectionStringUtil.OBJECT_NAME).toString().
	 * equalsIgnoreCase(objName)) list.add(BasicDBObject); } return list; }
	 * 
	 * private static List<String> partitionByObjName(List<BasicDBObject> objList) {
	 * List<String> nameList = new ArrayList<String>(); String type = null; for
	 * (BasicDBObject BasicDBObject : objList) { type =
	 * BasicDBObject.get(CollectionStringUtil.OBJECT_NAME).toString(); if
	 * (!typeIsExist(type, nameList)) nameList.add(type); } return nameList; }
	 * 
	 * private static boolean typeIsExist(String type, List<String> nameList) { for
	 * (String string : nameList) { if (type != null &&
	 * type.equalsIgnoreCase(string)) { return true; } } return false; }
	 * 
	 * private static void findObjRelation(BasicDBObject BasicDBObject,
	 * MongoCollection<BasicDBObject> collection, MongoCollection<BasicDBObject>
	 * relCollection, List<BasicDBObject> objList, List<BasicDBObject> relList) { if
	 * (BasicDBObject != null) objList.add(BasicDBObject); String parentId =
	 * BasicDBObject.get(CollectionStringUtil.UUID).toString();
	 * FindIterable<BasicDBObject> doc = findRelationByParentId(parentId,
	 * relCollection); String uuid; for (BasicDBObject BasicDBObject2 : doc) { if
	 * (BasicDBObject2 != null) relList.add(BasicDBObject2); uuid =
	 * BasicDBObject2.get(CollectionStringUtil.CHILD_ID).toString(); BasicDBObject
	 * findObjByUUID = findObjByUUID(uuid, collection); if (findObjByUUID == null)
	 * continue; findObjRelation(findObjByUUID, collection, relCollection, objList,
	 * relList); } }
	 * 
	 * private static BasicDBObject findObjByUUID(String uuid,
	 * MongoCollection<BasicDBObject> collection) { BasicDBObject find =
	 * collection.find(new BasicDBObject(CollectionStringUtil.UUID, uuid)).first();
	 * return find; }
	 * 
	 * private static FindIterable<BasicDBObject> findRelationByParentId(String
	 * parentId, MongoCollection<BasicDBObject> relCollection) {
	 * FindIterable<BasicDBObject> find = relCollection.find(new
	 * BasicDBObject(CollectionStringUtil.PARENT_ID, parentId)); return find; }
	 */
	public static List<BasicDBObject> findExcutor(String param, DBCollection collection, DBCollection relCollection,
			DBCursor find) {
		List<BasicDBObject> objList = new ArrayList<BasicDBObject>();
		List<BasicDBObject> relList = new ArrayList<BasicDBObject>();
		if (find == null)
			return null;
		for (DBObject basicDBObject : find) {
			findObjRelation((BasicDBObject) basicDBObject, collection, relCollection, objList, relList);
		}
		List<String> partitionByObjName = partitionByObjName(objList);
		Map<String, List<BasicDBObject>> typeObjMap = new HashMap<String, List<BasicDBObject>>();
		for (String string : partitionByObjName) {
			typeObjMap.put(string, partitionByObjName(objList, string));
		}
		List<BasicDBObject> mainList = typeObjMap.remove(param);
		setValueToMainObj(mainList, typeObjMap, relList);
		return mainList;
	}

	private static void setValueToMainObj(List<BasicDBObject> mainList, Map<String, List<BasicDBObject>> typeObjMap,
			List<BasicDBObject> relList) {
		Set<Entry<String, List<BasicDBObject>>> entrySet = typeObjMap.entrySet();
		List<BasicDBObject> valueList;
		String objName = null;
		List<BasicDBObject> value;
		for (BasicDBObject mainObj : mainList) {
			String parentId = mainObj.get(CollectionStringUtil.UUID).toString();
			for (Entry<String, List<BasicDBObject>> entry : entrySet) {
				valueList = new ArrayList<BasicDBObject>();
				objName = entry.getKey();
				value = entry.getValue();
				for (BasicDBObject doc : value) {
					if (isMatter(parentId, doc.get(CollectionStringUtil.UUID).toString(), relList)) {
						setValueToMainObj(value, typeObjMap, relList);
						valueList.add(doc);
					}
				}
				Object obj = isCollection(valueList);
				if (obj != null)
					mainObj.append(objName, obj);
			}
		}
	}

	private static Object isCollection(List<BasicDBObject> valueList) {
		if (valueList.size() == 0)
			return null;
		return valueList.size() > 1 ? valueList : valueList.get(0);
	}

	private static boolean isMatter(String parentId, String childId, List<BasicDBObject> relList) {
		for (BasicDBObject basicDBObject : relList) {
			if (parentId.equals(basicDBObject.get(CollectionStringUtil.PARENT_ID))
					&& childId.equals(basicDBObject.get(CollectionStringUtil.CHILD_ID))) {
				relList.remove(basicDBObject);
				return true;
			}
		}
		return false;
	}

	private static List<BasicDBObject> partitionByObjName(List<BasicDBObject> objList, String objName) {
		List<BasicDBObject> list = new ArrayList<BasicDBObject>();
		for (BasicDBObject basicDBObject : objList) {
			if (basicDBObject.get(CollectionStringUtil.OBJECT_NAME).toString().equalsIgnoreCase(objName))
				list.add(basicDBObject);
		}
		return list;
	}

	private static List<String> partitionByObjName(List<BasicDBObject> objList) {
		List<String> nameList = new ArrayList<String>();
		String type = null;
		for (BasicDBObject basicDBObject : objList) {
			type = basicDBObject.get(CollectionStringUtil.OBJECT_NAME).toString();
			if (!typeIsExist(type, nameList))
				nameList.add(type);
		}
		return nameList;
	}

	private static boolean typeIsExist(String type, List<String> nameList) {
		for (String string : nameList) {
			if (type != null && type.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	private static void findObjRelation(BasicDBObject BasicDBObject, DBCollection collection,
			DBCollection relCollection, List<BasicDBObject> objList, List<BasicDBObject> relList) {
		if (BasicDBObject != null)
			objList.add(BasicDBObject);
		String parentId = BasicDBObject.get(CollectionStringUtil.UUID).toString();
		DBCursor doc = findRelationByParentId(parentId, relCollection);
		String uuid;
		for (DBObject basicDBObject2 : doc) {
			if (basicDBObject2 != null)
				relList.add((BasicDBObject) basicDBObject2);
			uuid = basicDBObject2.get(CollectionStringUtil.CHILD_ID).toString();
			BasicDBObject findObjByUUID = (BasicDBObject) findObjByUUID(uuid, collection);
			if (findObjByUUID == null)
				continue;
			findObjRelation(findObjByUUID, collection, relCollection, objList, relList);
		}
	}

	private static DBObject findObjByUUID(String uuid, DBCollection collection) {
		DBObject find = collection.findOne(new BasicDBObject(CollectionStringUtil.UUID, uuid));
		return find;
	}

	private static DBCursor findRelationByParentId(String parentId, DBCollection relCollection) {
		DBCursor find = relCollection.find(new BasicDBObject(CollectionStringUtil.PARENT_ID, parentId));
		return find;
	}

}
