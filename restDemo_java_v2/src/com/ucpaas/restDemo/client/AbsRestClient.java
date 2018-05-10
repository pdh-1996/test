/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com.ucpaas.restDemo.client;

import com.ucpaas.restDemo.SysConfig;

public abstract class AbsRestClient {
	public String server=SysConfig.getInstance().getProperty("rest_server");
	
	/**
	 * 
	 * @param sid
	 * @param token
	 * @param appid
	 * @param templateid
	 * @param param
	 * @param mobile
	 * @param uid
	 * @return
	 */
	public abstract String sendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid);
	
	public StringBuffer getStringBuffer() {
		StringBuffer sb = new StringBuffer("https://");
		sb.append(server).append("/ol/sms");
		return sb;
	}
}
