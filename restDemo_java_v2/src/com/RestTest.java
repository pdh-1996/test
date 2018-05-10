/**
 * @author Tony
 * @date 2018-01-10
 * @project rest_demo
 */
package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;

public class RestTest {

	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}

	public static void testSendSms(String sid, String token, String appid, String templateid, String param,
			String mobile, String uid) {
		try {
			String result = InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试说明 启动main方法后，请在控制台输入数字(数字对应 相应的调用方法)，回车键结束 参数名称含义，请参考rest api 文档
	 * 
	 * @throws IOException
	 * @method main
	 */
	public static void main(String[] args) throws IOException {
		int value = 0;
		for (int param = RandomUtils.nextInt(10000);;) {
			value = param;
			if (value > 999) {
				break;
			}
		}
		String sid = "7c0e9b4f6a00632c06dd9ca8a672e9d5";
		String token = "a14e9cb16a38702a46eb105094356ac7";
		String appid = "e1c6dd38fc6a44f6a178d33e1bb8e55a";
		String templateid = "321217";
		String param = value + "";
		String mobile = "18617050746";
		String uid = "";
		testSendSms(sid, token, appid, templateid, param, mobile, uid);
	}
}
