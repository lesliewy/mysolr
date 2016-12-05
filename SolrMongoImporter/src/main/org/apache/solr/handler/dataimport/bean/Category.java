/**
 * Project Name:SolrMongoImporter  
 * File Name:Category.java  
 * Package Name:org.apache.solr.handler.dataimport.bean  
 * Date:Dec 4, 201611:34:29 AM  
 * Copyright (c) 2016, wy All Rights Reserved.  
 *  
 */
package org.apache.solr.handler.dataimport.bean;

/**
         * ClassName: Category <br/>  
         * Function: TODO ADD FUNCTION. <br/>  
         * Reason: TODO ADD REASON(可选). <br/>  
         * date: Dec 4, 2016 11:34:29 AM <br/>  
         *  
         * @author leslie  
         * @version   
         * @since version 1.0  
         */
public class Category {
	private String url = "";
	private int numofauthors = 0;
	private String name = "";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNumofauthors() {
		return numofauthors;
	}
	public void setNumofauthors(int numofauthors) {
		this.numofauthors = numofauthors;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
