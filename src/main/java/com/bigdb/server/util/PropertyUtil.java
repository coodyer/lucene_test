package com.bigdb.server.util;

import java.util.Properties;

public class PropertyUtil {

	public static String getConf(String key) {
		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/conf.properties")); /// 加载属性列表
			return (String) prop.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}