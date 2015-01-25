package cn.zlh.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存系统
 * @author 朱林虎
 *
 */
public class CacheDemo {
	
	private Map<String, Object> cache = new HashMap<String, Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	public Object getData(String key){
		
		rwl.readLock().lock();//需要读取数据的时候加上读锁，防止写入
		Object obj = null;
		try{
			obj = cache.get(key);
			if(obj == null){
				rwl.readLock().unlock();//没有获取到数据，释放读取，以便加上写锁
				rwl.writeLock().lock();//加上写锁,防止读取和其他写入
				try {
					if(obj == null){//再次判断空是为了防止其他线程在第一个线程释放写锁后再次写入数据
						obj = "abc";
					}
				}finally{
					rwl.writeLock().unlock();//释放写锁
				}
				rwl.readLock().lock();//加上读锁，防止写入
			}
		}finally{
			rwl.readLock().unlock();
		}
		
		return obj;
	}

}
