/**
 * Project Name:SolrMongoImporter  
 * File Name:GsonUtil.java  
 * Package Name:org.apache.solr.handler.dataimport.gson  
 * Date:Dec 4, 201611:35:40 AM  
 * Copyright (c) 2016, wy All Rights Reserved.  
 *  
 */
package org.apache.solr.handler.dataimport.gson;

import java.util.List;

import org.apache.solr.handler.dataimport.bean.Poem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
         * ClassName: GsonUtil <br/>  
         * Function: TODO ADD FUNCTION. <br/>  
         * Reason: TODO ADD REASON(可选). <br/>  
         * date: Dec 4, 2016 11:35:40 AM <br/>  
         *  
         * @author leslie  
         * @version   
         * @since version 1.0  
         */
public class GsonUtil {
	private static Gson gson = new Gson();
	// 将Json数据解析成相应的映射对象
	public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
		T result = gson.fromJson(jsonData, type);
		return result;
	}
	
	public static List<Poem> parsePoemsWithGson(String jsonArrayData){
		List<Poem> retList = gson.fromJson(jsonArrayData,
				new TypeToken<List<Poem>>() {
				}.getType());
		return retList;
	}
}
