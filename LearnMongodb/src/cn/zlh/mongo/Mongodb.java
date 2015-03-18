/**
 * 
 */
package cn.zlh.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-18 下午04:20:04      
 */
public class Mongodb {
	
	private static Mongo mongo = null;
	private static DB db = null;
	
	public Mongodb(String dbName) throws UnknownHostException, MongoException {
		mongo = new Mongo("127.0.0.1:27017");
		db = mongo.getDB(dbName);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnknownHostException, MongoException {
		Mongodb mongodb = new Mongodb("foobar");
		
		//获取数据库中的所有集合
//		Set<String> collNames = mongodb.getCollNames();
//		for(String collName : collNames){
//			System.out.println("collName:" + collName);
//		}
		//创建集合
//		mongodb.createCollection("javadb");
		
		//插入数据
//		DBObject dbObject = new BasicDBObject();
//		dbObject.put("name", "xiaoming");
//		dbObject.put("age", 22);
//		List<String> books = new ArrayList<String>();
//		books.add("EXTJS");
//		books.add("JAVA");
//		dbObject.put("books", books);
//		mongodb.insert(dbObject, "javadb");
		
//		List<DBObject> dbObjects = new ArrayList<DBObject>();
//		
//		DBObject dbObject1 = new BasicDBObject();
//		dbObject1.put("name", "jim");
//		dbObjects.add(dbObject1);
//		DBObject dbObject2 = new BasicDBObject();
//		dbObject2.put("name", "tom");
//		dbObjects.add(dbObject2);
//		mongodb.batchInsert(dbObjects, "javadb");
		
//		int count = mongodb.deleteById("55093d6e2cac7ba0d748e84a", "javadb");
//		System.out.println("删除条数：" + count);
		
//		DBObject dbObject = new BasicDBObject();
//		dbObject.put("name", "tom");
//		int count = mongodb.deleteByDbs(dbObject, "javadb");
//		System.out.println("删除条数：" + count);
		
		//更新
//		DBObject update = new BasicDBObject();
//		update.put("$set", new BasicDBObject("email", "skysea361@163.com"));
//		int count = mongodb.update(new BasicDBObject(), update, false, true, "javadb");
//		System.out.println("更新条数：" + count);
		
//		DBObject ref = new BasicDBObject();
//		ref.put("age", new BasicDBObject("$gt",26));
//		ref.put("e", new BasicDBObject("$lt",90));
//		DBCursor dbCursor = mongodb.find(ref, null, "persons");
//		while(dbCursor.hasNext()){
//			DBObject dbObject = dbCursor.next();
//			System.out.print(dbObject.get("name") + ">>");
//			System.out.print(dbObject.get("age") + ">>");
//			System.out.println(dbObject.get("e"));
//		}
		
		DBCursor dbCursor = mongodb.find(null, null, 0,3,"persons");
		while(dbCursor.hasNext()){
			DBObject dbObject = dbCursor.next();
			System.out.print(dbObject.get("name") + ">>");
			System.out.print(dbObject.get("age") + ">>");
			System.out.println(dbObject.get("e"));
		}
	
		
		mongodb.close();
	}
	
	/**
	 * 按条件分页查询数据
	 * @param ref
	 * @param keys
	 * @param collName
	 * @return
	 */
	public DBCursor find(DBObject ref,DBObject keys,Integer start,Integer limit,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		DBCursor dbCursor = dbCollection.find(ref, keys);
		return dbCursor.limit(limit).skip(start);
	}
	
	/**
	 * 按条件查询数据
	 * @param ref
	 * @param keys
	 * @param collName
	 * @return
	 */
	public DBCursor find(DBObject ref,DBObject keys,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		DBCursor dbCursor = dbCollection.find(ref, keys);
		return dbCursor;
	}
	
	
	/**
	 * 更新
	 * @param find
	 * @param update
	 * @param upIns
	 * @param isMuti
	 * @param collName
	 * @return
	 */
	public int update(DBObject find,DBObject update,boolean upIns,boolean isMuti,String collName){
		
		DBCollection dbCollection = db.getCollection(collName);
		int count = dbCollection.update(find, update, upIns, isMuti).getN();
		return count;
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @param collName
	 * @return
	 */
	public int deleteById(String id,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		DBObject dbObject = new BasicDBObject("_id",new ObjectId(id));
		int count = dbCollection.remove(dbObject).getN();
		return count;
	}
	
	/**
	 * 根据条件删除
	 * @param dbObject
	 * @param collName
	 * @return
	 */
	public int deleteByDbs(DBObject dbObject,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		int count = dbCollection.remove(dbObject).getN();
		return count;
	}
	
	/**
	 * 插入单个对象
	 * @param dbObject
	 * @param collName
	 */
	public void insert(DBObject dbObject,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		dbCollection.insert(dbObject);
	}
	
	/**
	 * 批量插入数据
	 * @param dbs
	 * @param collName
	 */
	public void batchInsert(List<DBObject> dbs,String collName){
		DBCollection dbCollection = db.getCollection(collName);
		dbCollection.insert(dbs);
	}
	
	/**
	 * 创建集合
	 * @param collName
	 */
	public void createCollection(String collName){
		DBObject dbObject = new BasicDBObject();
		db.createCollection(collName, dbObject);
	}
	
	/**
	 * 获取数据库中的集合
	 * @return
	 */
	public Set<String> getCollNames(){
		return db.getCollectionNames();
	}
	
	/**
	 * 关闭连接
	 */
	public void close(){
		mongo.close();
	}
}
