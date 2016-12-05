package org.apache.solr.handler.dataimport;


import static org.apache.solr.handler.dataimport.DataImportHandlerException.SEVERE;
import static org.apache.solr.handler.dataimport.DataImportHandlerException.wrapAndThrow;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOptions.Builder;
import com.mongodb.AggregationOptions.OutputMode;
import com.mongodb.AggregationOutput;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ReadPreference;
import com.mongodb.util.JSON;

/**
 * User: James
 * Date: 13/08/12
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */


public class MongoDataSource extends DataSource<Iterator<Map<String, Object>>> {

    private static final Logger LOG = LoggerFactory.getLogger(MongoDataSource.class);

    private DBCollection mongoCollection;
    private DB mongoDb;
    private Mongo mongoConnection;

    private DBCursor mongoCursor;

    @Override
    public void init(Context context, Properties initProps) {
   	  LOG.info("leslie, this is init()");
        String databaseName = initProps.getProperty(DATABASE);
        String host = initProps.getProperty(HOST, "localhost");
        String port = initProps.getProperty(PORT, "27017");
        String username = initProps.getProperty(USERNAME);
        String password = initProps.getProperty(PASSWORD);

        if (databaseName == null) {
            throw new DataImportHandlerException(SEVERE
                    , "Database must be supplied");
        }
        try {
            Mongo mongo = new Mongo(host, Integer.parseInt(port));
            mongo.setReadPreference(ReadPreference.secondaryPreferred());

            this.mongoConnection = mongo;
            this.mongoDb = mongo.getDB(databaseName);

            if (username != null) {
                if (this.mongoDb.authenticate(username, password.toCharArray()) == false) {
                    throw new DataImportHandlerException(SEVERE
                            , "Mongo Authentication Failed");
                }
            }

        } catch (UnknownHostException e) {
            throw new DataImportHandlerException(SEVERE
                    , "Unable to connect to Mongo");
        }
    }

    @Override
    public Iterator<Map<String, Object>> getData(String query) {
   	  LOG.info("leslie: this is getData(query) query: " + query);
   	  Iterator<Map<String, Object>> iter = null;
   	  String[] pipeStr = query.split("\\|");
   	  /** find **/
   	  if(pipeStr.length <= 1){
           DBObject queryObject = (DBObject) JSON.parse(query);
           LOG.debug("Executing MongoQuery: " + query.toString());

           long start = System.currentTimeMillis();
           mongoCursor = this.mongoCollection.find(queryObject);
           LOG.trace("Time taken for mongo :"
                   + (System.currentTimeMillis() - start));

           iter = new ResultSetIterator(mongoCursor).getIterator();
   	  }else{
      	  /** aggregation pipeline 调用aggregate(List<DBObject>() 方法编译不通过**/
   		  DBObject[] otherpipelines = new DBObject[pipeStr.length - 1];
   		  DBObject firstObject = (DBObject)JSON.parse(pipeStr[0]);
      	  for(int i = 1; i < pipeStr.length; i++){
      		  otherpipelines[i - 1] = (DBObject) JSON.parse(pipeStr[i]);
      	  }
      	  AggregationOutput out = this.mongoCollection.aggregate(firstObject, otherpipelines);
      	  LOG.info("leslie out: " + out);
      	  iter = new AggreResultSetIterator(out).getIterator();
   	  }
        return iter;
    }

    public Iterator<Map<String, Object>> getData(String query, String collection) {
   	  LOG.info("leslie: this is getData(query, collection) query: " + query + " collection: " + collection);
        this.mongoCollection = this.mongoDb.getCollection(collection);
        return getData(query);
    }
    private class ResultSetIterator {
        DBCursor MongoCursor;

        Iterator<Map<String, Object>> rSetIterator;

        public ResultSetIterator(DBCursor MongoCursor) {
            this.MongoCursor = MongoCursor;


            rSetIterator = new Iterator<Map<String, Object>>() {
                public boolean hasNext() {
                    return hasnext();
                }

                public Map<String, Object> next() {
                    return getARow();
                }

                public void remove() { // do nothing 
                }
            };


        }

        public Iterator<Map<String, Object>> getIterator() {
            return rSetIterator;
        }

        private Map<String, Object> getARow() {
            DBObject mongoObject = getMongoCursor().next();

            Map<String, Object> result = new HashMap<String, Object>();
            Set<String> keys = mongoObject.keySet();
            Iterator<String> iterator = keys.iterator();


            while (iterator.hasNext()) {
                String key = iterator.next();
                Object innerObject = mongoObject.get(key);

                result.put(key, innerObject);
            }

            return result;
        }

        private boolean hasnext() {
            if (MongoCursor == null)
                return false;
            try {
                if (MongoCursor.hasNext()) {
                    return true;
                } else {
                    close();
                    return false;
                }
            } catch (MongoException e) {
                close();
                wrapAndThrow(SEVERE, e);
                return false;
            }
        }

        private void close() {
            try {
                if (MongoCursor != null)
                    MongoCursor.close();
            } catch (Exception e) {
                LOG.warn("Exception while closing result set", e);
            } finally {
                MongoCursor = null;
            }
        }
    }
    
    private class AggreResultSetIterator {
       AggregationOutput out;
       Iterator<DBObject> iter;
       Iterator<Map<String, Object>> rSetIterator;

       public AggreResultSetIterator(AggregationOutput out) {
           this.out = out;
           iter = out.results().iterator();

           rSetIterator = new Iterator<Map<String, Object>>() {
               public boolean hasNext() {
                   return hasnext();
               }

               public Map<String, Object> next() {
                   return getARow();
               }

               public void remove() {/* do nothing */
               }
           };
       }

       public Iterator<Map<String, Object>> getIterator() {
           return rSetIterator;
       }

       private Map<String, Object> getARow() {
           DBObject mongoObject = iter.next();

           Map<String, Object> result = new HashMap<String, Object>();
           Set<String> keys = mongoObject.keySet();
           Iterator<String> iterator = keys.iterator();


           while (iterator.hasNext()) {
               String key = iterator.next();
               Object innerObject = mongoObject.get(key);

               result.put(key, innerObject);
           }

           return result;
       }

       private boolean hasnext() {
           if (out == null)
               return false;
           try {
               if (iter.hasNext()) {
                   return true;
               } else {
                   close();
                   return false;
               }
           } catch (MongoException e) {
               close();
               wrapAndThrow(SEVERE, e);
               return false;
           }
       }

       private void close() {
           try {
               if (out != null){
               	
               }
           } catch (Exception e) {
               LOG.warn("Exception while closing result set", e);
           } finally {
               out = null;
           }
       }
   }
    
    private DBCursor getMongoCursor() {
        return this.mongoCursor;
    }

    @Override
    public void close() {
        if (this.mongoCursor != null) {
            this.mongoCursor.close();
        }

        if (this.mongoConnection != null) {
            this.mongoConnection.close();
        }
    }


    public static final String DATABASE = "database";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

}

