/**
 * Project Name:SolrMongoImporter  
 * File Name:Author.java  
 * Package Name:org.apache.solr.handler.dataimport.bean  
 * Date:Dec 4, 201611:33:47 AM  
 * Copyright (c) 2016, wy All Rights Reserved.  
 *  
 */
package org.apache.solr.handler.dataimport.bean;

/**
         * ClassName: Author <br/>  
         * Function: TODO ADD FUNCTION. <br/>  
         * Reason: TODO ADD REASON(可选). <br/>  
         * date: Dec 4, 2016 11:33:47 AM <br/>  
         *  
         * @author leslie  
         * @version   
         * @since version 1.0  
         */
public class Author {
	private String url = "";
	private int numofpoems = 0;
	private String name = "";
	private String brief = "";
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getNumofpoems() {
		return numofpoems;
	}
	public void setNumofpoems(int numofpoems) {
		this.numofpoems = numofpoems;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
}
