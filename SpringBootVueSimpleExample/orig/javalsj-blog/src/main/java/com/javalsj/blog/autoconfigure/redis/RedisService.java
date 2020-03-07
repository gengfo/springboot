package com.javalsj.blog.autoconfigure.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @description Redis服务类
 * @author WANGJIHONG
 * @date 2018年3月29日 下午8:22:29
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
@Service
public class RedisService<T> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 默认缓存时间一天（秒）
	 */
	private static final long SECONDS_OF_ONE_DAY = 24 * 60 * 60L;

	// **************************缓存单个实体对象数据结构***********************************
	
	/**
	 * 将 key，value 存放到redis数据库中，默认设置过期时间为一天
	 *
	 * @param key
	 *            缓存键
	 * @param value
	 *            缓存值
	 */
	public void setCache(String key, T value) {
		setCache(key, value, SECONDS_OF_ONE_DAY);
	}

	/**
	 * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
	 *
	 * @param key
	 *            缓存键
	 * @param value
	 *            缓存值
	 * @param expireTime
	 *            失效时间，单位是秒
	 */
	public void setCache(String key, T value, long expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 判断 key是否在 redis 数据库中
	 * 
	 * @param key
	 *            缓存键
	 * @return true:存在，false:不存在
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获取 key 对应的字符串
	 * 
	 * @param key
	 *            缓存键
	 * @return T 缓存值对象，该类必须存在无参构造方法，反序列化时使用，否则报错。
	 */
	@SuppressWarnings("unchecked")
	public T get(String key) {
		Object value = redisTemplate.opsForValue().get(key);
		if (Objects.isNull(value)) {
			return null;
		}
		return (T) value;
	}

	/**
	 * @param keys
	 *            多个缓存键
	 * @return List<T> 多个缓存值对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> multiGet(Collection<String> keys) {
		if (CollectionUtils.isEmpty(keys)) {
			return null;
		}
		List<Object> values = redisTemplate.opsForValue().multiGet(keys);
		if (CollectionUtils.isEmpty(values)) {
			return null;
		}
		List<T> tList = new ArrayList<T>();
		tList = values.stream().map(value -> (T) value).collect(Collectors.toList());
		return tList;
	}

	/**
	 * 获得 key 对应的键值，并更新缓存时间，时间长度为默认值
	 * 
	 * @param key
	 *            缓存键
	 * @return 缓存值
	 */
	public T getAndUpdateTime(String key) {
		T value = get(key);
		if (value != null) {
			setCache(key, value);
		}
		return value;
	}

	/**
	 * 批量删除key，根据key精确匹配
	 * 
	 * @param keys
	 *            多个缓存键
	 * @return long 删除的数量
	 */
	public long delete(String... keys) {
		if (keys == null || keys.length == 0) {
			return 0L;
		}
		return redisTemplate.delete(List.of(keys));
	}

	// **************************缓存List Set Map对象数据结构***********************************

	/**
	 * 缓存List数据
	 * 
	 * @param key
	 *            缓存的键值
	 * @param values
	 *            待缓存的List数据
	 */
	public void setCacheList(String key, List<T> valueList) {
		if (CollectionUtils.isEmpty(valueList)) {
			return;
		}
		ListOperations<String, Object> opsForList = redisTemplate.opsForList();
		valueList.stream().forEach(value -> {
			opsForList.rightPush(key, value);
		});
	}

	/**
	 * 获得缓存的list对象
	 * 
	 * @param key
	 *            缓存的键值
	 * @return 缓存键值对应的数据
	 */
	@SuppressWarnings("unchecked")
	public List<T> getCacheList(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		List<T> cacheList = new ArrayList<T>();
		ListOperations<String, Object> opsForList = redisTemplate.opsForList();
		Long size = opsForList.size(key);
		for (int i = 0; i < size; i++) {
			cacheList.add((T) opsForList.leftPop(key));
		}
		return cacheList;
	}

	/**
	 * 缓存Set
	 * 
	 * @param key
	 *            缓存键值
	 * @param dataSet
	 *            缓存的数据
	 */
	public void setCacheSet(String key, Set<T> valueSet) {
		if (CollectionUtils.isEmpty(valueSet)) {
			return;
		}
		BoundSetOperations<String, Object> boundSetOps = redisTemplate.boundSetOps(key);
		valueSet.stream().forEach(value -> {
			boundSetOps.add(value);
		});
	}

	/**
	 * 获得缓存的Set
	 */
	@SuppressWarnings("unchecked")
	public Set<T> getCacheSet(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		Set<T> cacheSet = new HashSet<T>();
		BoundSetOperations<String, Object> boundSetOps = redisTemplate.boundSetOps(key);
		Long size = boundSetOps.size();
		for (int i = 0; i < size; i++) {
			cacheSet.add((T) boundSetOps.pop());
		}
		return cacheSet;
	}

	/**
	 * 缓存Map
	 * 
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public void setCacheMap(String key, Map<String, T> valueMap) {
		if (CollectionUtils.isEmpty(valueMap)) {
			return;
		}
		HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
		valueMap.forEach((mapKey, mapValue) -> {
			hashOperations.put(key, mapKey, mapValue);
		});
	}

	/**
	 * 缓存Map
	 */
	public void setCacheMap(String key, String mapKey, T mapValue) {
		if (StringUtils.isEmpty(mapKey)) {
			return;
		}
		HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(key, mapKey, mapValue);
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public Map<String, T> getCacheMap(String key) {
		Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
		Map<String, T> map = new HashMap<>(entries.size());
		entries.forEach((mapKey, mapValue) -> {
			map.put(String.valueOf(mapKey), (T) mapValue);
		});
		return map;
	}

	/**
	 * 获得缓存的Map
	 * 
	 * @param key
	 * @param hashOperation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getCacheMapValue(String key, String mapKey) {
		T value = (T) redisTemplate.opsForHash().entries(key).get(mapKey);
		return value;
	}

	/**
	 * 删除对象
	 * 
	 * @param key
	 *            缓存的键值
	 * @param key
	 *            缓存的map键值
	 */
	public void deleteCacheMap(String key, String mapKey) {
		HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
		if (hashOperations.hasKey(key, mapKey)) {
			hashOperations.delete(key, mapKey);
		}
	}

}
