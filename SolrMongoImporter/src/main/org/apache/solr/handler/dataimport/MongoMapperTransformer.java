package org.apache.solr.handler.dataimport;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: James
 * Date: 15/08/12
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class MongoMapperTransformer extends Transformer {
	private static final Logger LOG = LoggerFactory.getLogger(MongoMapperTransformer.class);
	
    @Override
    public Object transformRow(Map<String, Object> row, Context context) {
   	 LOG.info("this is transformRow() row: " + row);
        for (Map<String, String> map : context.getAllEntityFields()) {
            String mongoFieldName = map.get(MONGO_FIELD);
            if (mongoFieldName == null)
                continue;

            String columnFieldName = map.get(DataImporter.COLUMN);
            /*  对于.分隔的字段认为是嵌套的, 这里不考虑通用性了 */
            /*
            String[] phrases = mongoFieldName.split("\\.");
            Author author = null;
            Category category = null;
            List<Poem> poems = null;
            if(phrases.length > 1){
            	if("author".equalsIgnoreCase(phrases[0])){
            		author = GsonUtil.parseJsonWithGson(row.get(phrases[0]).toString(), Author.class);
            		if("name".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, author.getName());
            		}else if("url".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, author.getUrl());
            		}else if("numofpoems".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, author.getNumofpoems());
            		}else if("brief".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, author.getBrief());
            		}
            	}else if("category".equalsIgnoreCase(phrases[0])){
            		category = GsonUtil.parseJsonWithGson(row.get(phrases[0]).toString(), Category.class);
            		if("name".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, category.getName());
            		}else if("url".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, category.getUrl());
            		}else if("numofauthors".equalsIgnoreCase(phrases[1])){
            			row.put(columnFieldName, category.getNumofauthors());
            		}
            	}
            }else if("poems".equalsIgnoreCase(phrases[0])){
            	LOG.info("leslie poemsa" + row.get(phrases[0]).toString());
            	poems = GsonUtil.parsePoemsWithGson(row.get(phrases[0]).toString());
            	int size = poems.size();
            	LOG.info("leslie size: " + size);
            	for(int i = 0; i < size; i++){
            		Poem poem = poems.get(i);
            		row.put("poem." + i + ".name", poem.getName());
            		row.put("poem." + i + ".url", poem.getUrl());
            		row.put("poem." + i + ".content", poem.getContent());
            		row.put("poem." + i + ".appreciation", poem.getAppreciation());
            	}
            }else{
               row.put(columnFieldName, row.get(mongoFieldName));
            }
            */
            row.put(columnFieldName, row.get(mongoFieldName));
        }
        /*
        row.remove("author");
        row.remove("category");
        row.remove("poems");
        */
        LOG.info("leslie processed row:" + row);
        return row;
    }

    public static final String MONGO_FIELD = "mongoField";
}
