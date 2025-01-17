package com.ts.zx.ig;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class XMLUtils {
	
	public static String kepserverData2XML(String equipmentName, String paramName, String paramValue) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("equipments");
		Element equipment = root.addElement("equipment");
		equipment.addElement("name").addText(equipmentName);
		
		Element params = equipment.addElement("params");
		Element param = params.addElement("param");
		param.addElement("name").addText(paramName);
		param.addElement("value").addText(paramValue);
		
		return document.getRootElement().asXML();
	}
	
	public static String fboxData2XML(String text) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("equipments");
		Element equipment = root.addElement("equipment");
		
		JSONArray array = JSONArray.parseArray(text);
		
		//盒子boxSessionId用于区分数据所属的设备
		String boxSessionId = array.getString(0);
		equipment.addElement("name").addText(boxSessionId);
		
		//设备类型
//		equipment.addElement("type").addText("PLC");
		
		JSONArray array1 = array.getJSONArray(1);
		Element params = equipment.addElement("params");
		
		for (Iterator<Object> iterator = array1.iterator(); iterator.hasNext();) {
			JSONObject json1 = (JSONObject) iterator.next();
			Element param = params.addElement("param");
			
			String id = json1.getString("id");
			String value = json1.getString("value");
			String name = json1.getString("name");
			//String t = json1.getString("t");
			
			param.addElement("id").addText(id);
			param.addElement("name").addText(name);
			param.addElement("value").addText(value);
			//param.addElement("t").addText(t);
			
		}
		return document.getRootElement().asXML();
		
	}
	
}
