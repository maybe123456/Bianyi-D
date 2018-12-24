package com.example.baseapplication;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public abstract class GsonUtils {

	/**
	 * 解析Gson对象，
	 *
	 * @param json
	 * @param strategy
	 *            替换字段
	 * @param type
	 *            泛型
	 * @return
	 */
	public static <T> T parseTObject(String json, FieldNamingStrategy strategy,
                                     Type type) {
		GsonBuilder build = new GsonBuilder();
		if (null != strategy) {
			build.setFieldNamingStrategy(strategy);
		}
		return build.create().fromJson(json, type);
	}

	public static String parseObjToString(Object obj){
		String result = "";
		try {
			Gson gson = new Gson();
			result = gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static <T> T getPerson(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}

}
