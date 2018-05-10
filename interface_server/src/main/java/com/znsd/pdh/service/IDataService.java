package com.znsd.pdh.service;

import java.util.Map;

public interface IDataService {

	void save(String json) throws Exception;

	/**
	 * @return
	 */
	public String findAllDriver() throws Exception;

	/**
	 * @param param
	 * @return
	 */
	public String findDriverByType(String param) throws Exception;

	/**
	 * 按照对象类型与指定条件查询
	 * 
	 * @param dataType
	 *            传入对象类型
	 * @param query
	 *            传入条件集合
	 * @return 返回所需json字符串
	 */
	public String find(String dataType, Map<String, Object> query) throws Exception;

	/**
	 * 根据数据类型查找此类型所有数据，包括子类型
	 * 
	 * @param dataType
	 *            传入数据类型
	 * @return 返回所需json字符串
	 */
	public String findyAll(String dataType) throws Exception;

	/**
	 * 根据对象id和对象数据类型查找
	 * 
	 * @param dataType
	 *            传入数据类型
	 * @param uuid
	 *            传入uuid
	 * @return 返回所需json字符串
	 */
	public String findById(String dataType, String uuid) throws Exception;

}
