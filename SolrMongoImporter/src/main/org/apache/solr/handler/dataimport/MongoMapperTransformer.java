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
	
	private static int id = 1;
	
    @Override
    public Object transformRow(Map<String, Object> row, Context context) {
//   	 LOG.info("this is transformRow() row: " + row);
        for (Map<String, String> map : context.getAllEntityFields()) {
            String mongoFieldName = map.get(MONGO_FIELD);
            if (mongoFieldName == null)
                continue;
            String columnFieldName = map.get(DataImporter.COLUMN);
            /*  unwind的结果中同一个数组的记录的_id都是一样的，需要做下处理. */
            if("_id".equalsIgnoreCase(mongoFieldName)){
            	row.put(columnFieldName, String.valueOf(id++));
            	continue;
            }
            row.put(columnFieldName, row.get(mongoFieldName));
        }
        /*
        row.remove("author");
        row.remove("category");
        row.remove("poems");
        */
//        LOG.info("leslie processed row:" + row);
        return row;
    }

    public static final String MONGO_FIELD = "mongoField";
}
