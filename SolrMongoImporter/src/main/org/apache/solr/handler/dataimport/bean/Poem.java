/**
 * Project Name:SolrMongoImporter  
 * File Name:Poem.java  
 * Package Name:org.apache.solr.handler.dataimport.bean  
 * Date:Dec 4, 20162:24:52 PM  
 * Copyright (c) 2016, wy All Rights Reserved.  
 *  
 */
package org.apache.solr.handler.dataimport.bean;

import java.util.List;

/**
         * ClassName: Poem <br/>  
         * Function: TODO ADD FUNCTION. <br/>  
         * Reason: TODO ADD REASON(可选). <br/>  
         * date: Dec 4, 2016 2:24:52 PM <br/>  
         *  
         * @author leslie  
         * @version   
         * @since version 1.0  
         */
public class Poem {
	private String url = "";
	private String content = "";
	private String name = "";
	private String appreciation = "";
	private List<String> tags = null;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppreciation() {
		return appreciation;
	}
	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
