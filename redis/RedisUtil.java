package com.hisense.base.common.util;

import org.apache.poi.ss.formula.functions.T;
import org.apache.xpath.operations.Bool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.*;

import java.io.*;
import java.util.*;

public class RedisUtil {
	private static JedisPool pool = null;

	private static JedisCluster jedisCluster;

	/**
	 * 构建redis连接池
	 * 
	 * @return JedisPool
	 */
	public static synchronized JedisPool getPool() {
		try {
			Resource resource = new ClassPathResource("/conf/redis.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if (pool == null) {
				JedisPoolConfig config = new JedisPoolConfig();
				// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取
				config.setMaxTotal(Integer.parseInt(props.getProperty("maxTotal").toString()));
				// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
				config.setMaxIdle(Integer.parseInt(props.getProperty("maxIdle").toString()));
				// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
				config.setMaxWaitMillis(Long.parseLong(props.getProperty("maxWaitMillis").toString()) * 1000);
				// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
				config.setTestOnBorrow(Boolean.getBoolean(props.getProperty("testOnBorrow").toString()));
				int rediPort = Integer.valueOf(props.getProperty("redisPort").toString()).intValue();
				//pool = new JedisPool(config, props.getProperty("redisAddrees").toString(), rediPort, Protocol.DEFAULT_TIMEOUT, props.getProperty("redisPassword").toString());
				pool = new JedisPool(config, props.getProperty("redisAddrees").toString(), rediPort, Protocol.DEFAULT_TIMEOUT);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pool;
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (pool == null)
			getPool();
	}

	/**
	 * 返还到连接池
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		String value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}
	
	/**
	 * 获取keys
	 * @return
	 */
	public static Set<String> getKeys(String pattern) {
		Set<String> value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.keys(pattern);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}
	
	
	/**
	 * 获取数据
	 * @param key
	 * @return
	 */
	public static byte[] get(byte[] key) {
		byte[] value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}	

	/**
	 * 设置数据
	 * @param key
	 * @return
	 */
	public static void set(String key, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 设置数据
	 * @param key
	 * @return
	 */
	public static void set(byte[] key, byte[] value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}	
	
	/**
	 * 删除数据
	 * @param key
	 * @return
	 */
	public static void del(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}	

	/**
	 * 获取数据
	 * @return
	 */
	public static Set<String> hexist(String hasKey) {
		Set<String> value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.hkeys(hasKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}

	/**
	 * 获取数据
	 * @return
	 */
	public static String hget(String hasKey, String fieldKey) {
		String value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.hget(hasKey, fieldKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}

	/**
	 * 获取数据
	 * @return
	 */
	public static void hset(String hasKey, String fieldKey, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.hset(hasKey, fieldKey, value);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	public static void setMap(String key, Map map){
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ByteArrayOutputStream bai = new ByteArrayOutputStream();
			ObjectOutputStream obi = new ObjectOutputStream(bai);
			obi.writeObject(map);
			byte[] byt = bai.toByteArray();
			// 将map存入redis中
			jedis.set(key.getBytes(), byt);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	public static Map<String, Class> getMap(String key,Class c){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Class> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			Map<String, Class> result = (Map<String, Class>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return rmap;
	}

	public static Map<String,Map<String,Class>> getMap1(String key,Class c){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Map<String,Class>> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			Map result = (Map<String, Map<String,Class>>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return rmap;
	}


	public static void setList(String key, List list){
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ByteArrayOutputStream bai = new ByteArrayOutputStream();
			ObjectOutputStream obi = new ObjectOutputStream(bai);
			obi.writeObject(list);
			byte[] byt = bai.toByteArray();
			// 将map存入redis中
			jedis.set(key.getBytes(), byt);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	public static List<Class> getList(String key,Class c){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Class> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			List<Class> result = (List<Class>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return new ArrayList<>();
	}

	public static List<Map<String,Class>> getMapList(String key,Class c){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Class> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			List<Map<String,Class>> result = (List<Map<String,Class>>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return new ArrayList<>();
	}

	public static Map<String,List<Map<Integer,String>>> getListMap(String key){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Class> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			Map<String,List<Map<Integer,String>>> result = (Map<String,List<Map<Integer,String>>>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return new HashMap<>();
	}

	public static Map<String,List<Class>> getClassListMap(String key,Class c){
		JedisPool pool = null;
		Jedis jedis = null;
		ObjectInputStream oii = null;
		ByteArrayInputStream bis = null;
		Map<String, Class> rmap = new HashMap<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			//获取map
			byte[] personByte = jedis.get(key.getBytes());

			//转换成输入字节流
			bis = new ByteArrayInputStream(personByte);
			oii = new ObjectInputStream(bis);
			Map<String,List<Class>> result = (Map<String,List<Class>>) oii.readObject();
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return new HashMap<>();
	}

	public static Long incr(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long result = 0L;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				result = jedis.incr(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return result;
	}

	public static Boolean existKey(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				return jedis.exists(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return false;
	}



	/**
	 * 
	 * 删除hashKey删除hash集合
	 * 
	 * @param hasKey
	 * @return
	 */
	public static void hdel(String hasKey) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.del(hasKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 删除集合中的某条数据
	 * @param hasKey
	 * @param fieldKey
	 * @return
	 */
	public static void hdel(String hasKey, String fieldKey) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.hdel(hasKey, fieldKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public static byte[] hget(byte[] hasKey, byte[] fieldKey) {
		byte[] value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.hget(hasKey, fieldKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;
	}

	/**
	 * 获取数据
	 * @return
	 */
	public static void hset(byte[] hasKey, byte[] fieldKey, byte[] value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.hset(hasKey, fieldKey, value);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	/**
	 * 
	 * 删除hashKey删除hash集合
	 * 
	 * @param hasKey
	 * @return
	 */
	public static void hdel(byte[] hasKey) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.del(hasKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 删除集合中的某条数据
	 * @param hasKey
	 * @param fieldKey
	 * @return
	 */
	public static void hdel(byte[] hasKey, byte[] fieldKey) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.hdel(hasKey, fieldKey);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}	
	
	/**
	 * 设置失效时间
	 * @param key
	 * @param expiration 失效时间（秒）
	 */
	public static void expire(String key,int expiration) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.expire(key, expiration);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}
	
	/**
	 * 设置失效时间
	 * @param key
	 * @param expiration 失效时间（秒）
	 */
	public static Object expire(byte[] key,int expiration) {
		Object value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.expire(key, expiration);
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return value;
	}
	
	public static int dbSize(){
		int size = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			size =  jedis.dbSize().intValue();
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return size;
	}
	
	public static void flush() {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.flushDB();
			jedis.flushAll();
		} catch (Exception e) {
			// 释放redis对象
			if(pool!=null && jedis!=null){
				pool.returnBrokenResource(jedis);
			}
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

}
