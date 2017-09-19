package com.ts.zx.ig.fbox;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class FBoxHelper {
	
	/**
	 * 用户登陆，返回token
	 * @return
	 */
	public static String login() {

		String accessToken = null;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(FBoxConfig.ACCESS_TOKEN_URL);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("username", FBoxConfig.USERNAME));
		params.add(new BasicNameValuePair("password", FBoxConfig.PASSWORD));
		params.add(new BasicNameValuePair("scope", FBoxConfig.ACCESS_TOKEN_SCOPE));
		params.add(new BasicNameValuePair("client_id", FBoxConfig.CLIENT_ID));
		params.add(new BasicNameValuePair("client_secret", FBoxConfig.CLIENT_SECRET));
		params.add(new BasicNameValuePair("grant_type", FBoxConfig.ACCESS_TOKEN_GRANT_TYPE));
		
		UrlEncodedFormEntity formEntity = null;
		
		try {
			formEntity = new UrlEncodedFormEntity(params, "UTF-8");
			formEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(formEntity);
			System.out.println("executing request " + httpPost.getURI());
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				String result = EntityUtils.toString(responseEntity, "UTF-8");
				System.out.println("response content: " + result);
				JSONObject jsonObject = JSONObject.parseObject(result);
				accessToken = jsonObject.getString("access_token");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return accessToken;
	}
	
	/**
	 * 获取盒子监控点数据
	 * @param accessToken
	 * @return
	 */
	public static String listMonitoringSite(String accessToken, String boxuid) {
		
		String result = null;
		String url = FBoxConfig.API_BASE_URL + "box/" + boxuid + "/dmon/def/grouped";
		String authorization = "Bearer" + " " + accessToken;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Authorization", authorization);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				result = EntityUtils.toString(responseEntity, "UTF-8");
				System.out.println("response content: " + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
	/**
	 * 获取盒子报警列表
	 * @param accessToken
	 * @return
	 */
	public static String listAlarm(String accessToken, String boxuid) {
		
		String result = null;
		String url = FBoxConfig.API_BASE_URL + "box/" + boxuid + "/alarm/def";
		String authorization = "Bearer" + " " + accessToken;
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Authorization", authorization);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				result = EntityUtils.toString(responseEntity, "UTF-8");
				System.out.println("response content: " + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return result;
	}
	
}
