/**
 * 
 */
package cn.zlh.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-5-12 上午10:56:55      
 */
public class TestRedis {
	
	private Jedis jedis;
	
	public TestRedis(){
		jedis = new Jedis("10.0.1.37", 6379);
		jedis.auth("zhulinhu");
	}
	
	//string类型的操作
	public void testString(){
		jedis.set("name", "zhangsan");
		System.out.println("name:" + jedis.get("name"));
		
		jedis.append("name", " is a student");
		System.out.println("name:" + jedis.get("name"));
		Long result = jedis.del("name");
		System.out.println("reslut:" + result);
		jedis.mset("name","wangwu","sex","male","age","22", "qq","123456");
		jedis.incr("age");
		System.out.println("name:" + jedis.get("name") + ",sex:" + jedis.get("sex") + ",age:" + jedis.get("age") + ",qq:" + jedis.get("qq"));
	}
	
	//hash类型的操作
	public void testMap(){
		Map<String, String> user1 = new HashMap<String, String>();
		user1.put("name", "zhangsan");
		user1.put("age", "25");
		user1.put("qq", "2755666");
		user1.put("tel", "66515511");
		//添加一个hash类型的数据，key为map里面的key
		jedis.hmset("user1", user1);
		
		List<String> values = jedis.hmget("user1", "name","age");
		System.out.println(values);
		
		jedis.hdel("user1", "qq");
		System.out.println("qq:" + jedis.hget("user1", "qq"));
		
		System.out.println(jedis.hlen("user1"));
		System.out.println(jedis.hexists("user1","age"));
		jedis.hincrBy("user1", "age", 2);
		System.out.println(jedis.hkeys("user1"));
		System.out.println(jedis.hvals("user1"));
	}
	
	//list类型队列和栈的操作
	public void testList(){
		
		jedis.del("student");
		//从头部添加数据
		jedis.lpush("student", "zhangsan");
		jedis.lpush("student", "lisi");
		jedis.lpush("student", "wuwang");
		
		System.out.println(jedis.lrange("student", 0, -1));
		//从尾部添加数据
		jedis.rpush("student", "mada");
		jedis.rpush("student", "suner");
		jedis.rpush("student", "zhaoliu");
		
		System.out.println(jedis.lrange("student", 0, -1));
	}
	
	//set类型无序集合的操作
	public void testSet(){
		jedis.sadd("user", "mada");
		jedis.sadd("user", "suner");
		jedis.sadd("user", "zhangsan");
		jedis.sadd("user", "lisi");
		jedis.sadd("user", "wangwu");
		jedis.sadd("user", "zhaoliu");
		
		System.out.println(jedis.smembers("user"));
		
		jedis.srem("user", "lisi");
		
		System.out.println(jedis.smembers("user"));
		System.out.println(jedis.sismember("user", "lisi"));//判断元素是否存在
		System.out.println(jedis.srandmember("user"));//随机取出一个元素
		System.out.println(jedis.scard("user"));//获取元素的数量
	}
	
	public void testRedisPool(){
		Jedis jedis1 = RedisUtil.getJedis();
		System.out.println(jedis1.get("name"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestRedis testRedis = new TestRedis();
		//testRedis.testString();
		//testRedis.testMap();
		//testRedis.testList();
		//testRedis.testSet();
		testRedis.testRedisPool();
	}

}
