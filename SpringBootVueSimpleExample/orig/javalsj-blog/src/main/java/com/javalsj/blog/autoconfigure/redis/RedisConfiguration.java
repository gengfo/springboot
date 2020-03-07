package com.javalsj.blog.autoconfigure.redis;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description 缓存配置类（实现一二级分布式缓存）
 * @author WANGJIHONG
 * @date 2018年3月26日 下午7:43:50
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 配置类说明： <br/>
 *       1.缓存系统的用来代替直接访问数据库，用来提升系统性能，减小数据库复杂。早期缓存跟系统在一个虚拟机里，这样内存访问，速度最快。
 *       后来应用系统水平扩展，缓存作为一个独立系统存在，如redis，但是每次从缓存获取数据，都还是要通过网络访问才能获取，效率相对于早先从内存里获取，还是差了点。
 *       如果一个应用，比如传统的企业应用，一次页面显示，要访问数次redis，那效果就不是特别好，因此，现在有人提出了一二级缓存。
 *       即一级缓存跟系统在一个虚拟机内，这样速度最快。二级缓存位于redis里，当一级缓存没有数据的时候，再从redis里获取，并同步到一级缓存里。
 *       <br/>
 *       <br/>
 *       2.springboot提供一个概念CacheManager&Cache用来表示缓存，并提供了多达8种实现，但由于缺少一二级缓存，因此，需要在Redis基础上扩展，因此实现了MyRedisCacheManger,以及MyRedisCache，增加一个本地缓存。
 *       一二级缓存需要解决的的一个问题是缓存更新的时候，必须通知其他节点的springboot应用缓存更新。这里可以用Redis的 Pub/Sub
 *       功能来实现，具体可以参考listenerAdapter方法实现。 使用的时候，需要配置如下，这样，就可以使用缓存了，性能杠杠的好。 <br/>
 *       <br/>
 *       3.Cache 通常有如下组件构成
 *       ->CacheManager：用来创建，管理，管理多个命名唯一的Cache。如可以有组织机构缓存，菜单项的缓存，菜单树的缓存等 <br/>
 *       ->Cache：类似Map那样的Key—Value存储结构，Value部分 通常包含了缓存的对象，通过Key来取得缓存对象 <br/>
 *       ->缓存项：存放在缓存里的对象，常常需要实现序列化接口，以支持分布式缓存。 <br/>
 *       ->Cache存储方式：缓存组件的可以将对象放到内存，也可以是其他缓存服务器，Spring Boot
 *       提供了一个基于ConcurrentMap的缓存，同时也集成了Redis,EhCache 2.x,JCache缓存服务器等 <br/>
 *       ->缓存策略：通常Cache 还可以有不同的缓存策略，如设置缓存最大的容量，缓存项的过期时间等 <br/>
 *       ->分布式缓存：缓存通常按照缓存数据类型存放在不同缓存服务器上，或者同一类型的缓存，按照某种算法，不同key的数据放在不同的缓存服务器上。
 *       <br/>
 *       ->Cache Hit，当从Cache中取得期望的缓存项，我们通常称之为缓存命中。如果没有命中我们称之为Cache <br/>
 *       ->Miss：意味着需要从数据来源处重新取出并放回Cache中 Cache Miss：缓存丢失，根据Key没有从缓存中找到对应的缓存项
 *       Cache <br/>
 *       ->Evication：缓存清除操作。 Hot
 *       Data，热点数据，缓存系统能调整算法或者内部存储方式，使得将最有可能频繁访问的数据能尽快访问到。 <br/>
 *       ->On-Heap：Java分配对象都是在堆内存里，有最快的获取速度。由于虚拟机的垃圾回收管理，缓存放过多的对象会导致垃圾回收时间过长，从而有可能影响性能。
 *       <br/>
 *       ->Off-Heap：堆外内存，对象存放到在虚拟机分配的堆外内存，因此不受垃圾回收管理的管理，不影响系统系统，但堆外内存的对象要被使用，还要序列化成堆内对象。很多缓存工具会把不常用的对象放到堆外，把热点数据放到堆内。
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

	private Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

	/**
	 * 注解@Cache key生成规则
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	/**
	 * 初始化Redis模板
	 * 
	 * @memo Redis序列化常见的方式： <br/>
	 *       1.JdkSerializationRedisSerializer： 使用JDK提供的序列化功能。
	 *       优点是反序列化时不需要提供类型信息(class)，但缺点是序列化后的结果非常庞大，是JSON格式的5倍左右，这样就会消耗redis服务器的大量内存。<br/>
	 *       2.OxmSerializer： xml序列化的时间相对较长。<br/>
	 *       3.Jackson2JsonRedisSerializer：
	 *       使用Jackson库将对象序列化为JSON字符串。优点是速度快，序列化后的字符串短小精悍。但缺点也非常致命，那就是此类的构造函数中有一个类型参数，
	 *       必须提供要序列化对象的类型信息(.class对象)，在反序列化过程中用到了对象类型信息，如果用Jackson2JsonRedisSerializer方案，则必须给每一种domain对象都配置一个Serializer，
	 *       即如果我的应用里有100种domain对象，那就必须在spring配置文件中配置100个Jackson2JsonRedisSerialize，这显然是不现实的。<br/>
	 *       4.GenericToStringSerializer:
	 *       可以将任何对象泛化为字符串并序列化，相当于Jackson2JsonRedisSerialize的升级版，无需为每一种domain对象都配置一个Serializer。<br/>
	 *       5.除此之外也可以自定义序列化类。<br/>
	 *       综合比较选型GenericToStringSerializer序列化方式。
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		// 创建Redis模板配置
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		// 定义value的序列化方式
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// 缺少json的某个字段属性忽略
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(
				objectMapper);
		redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		return redisTemplate;
	}

	/**
	 * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行
	 * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
	 */
	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
			@Override
			public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
				logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
				logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
				logger.error("redis异常：key=[{}]", key, e);
			}

			@Override
			public void handleCacheClearError(RuntimeException e, Cache cache) {
				logger.error("redis异常：", e);
			}
		};
		return cacheErrorHandler;
	}

}