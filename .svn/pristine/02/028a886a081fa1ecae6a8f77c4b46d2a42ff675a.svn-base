package com.ts.zx.ig.kepserver;

import org.apache.commons.lang.StringUtils;

public class KepserverDataHelper {
	
	/**
	 * 从kepserver设备数据中提取设备名称
	 * @param itemNodeId
	 * @return
	 */
	public static String parseEquipmentName(String itemNodeId) {
		String str = "";
		if (StringUtils.isNotBlank(itemNodeId)) {
			str = itemNodeId.substring(itemNodeId.indexOf(".")+1, itemNodeId.lastIndexOf("."));
		}
		return str;
	}
	
	/**
	 * 从kepserver设备数据中提取设备参数名称
	 * @param itemNodeId
	 * @return
	 */
	public static String parseParamName(String itemNodeId) {
		String str = "";
		if (StringUtils.isNotBlank(itemNodeId)) {
			str = itemNodeId.substring(itemNodeId.lastIndexOf(".")+1, itemNodeId.length());
		}
		return str;
	}
	
	
}
