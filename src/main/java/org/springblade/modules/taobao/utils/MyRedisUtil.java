package org.springblade.modules.taobao.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自编redisUtil
 *
 * @author SeventySeven
 * @since 2020-08-25
 */
@Component
@AllArgsConstructor
public class MyRedisUtil {
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 给key添加过期时间 timeUnit自定义单位
	 *
	 * @param key      redis key
	 * @param time     time
	 * @param timeUnit 时间单位
	 * @return true 成功 false 不成功
	 */
	public Boolean expire(String key, Long time, TimeUnit timeUnit) {
		if (time < 0) {
			return false;
		}
		return redisTemplate.expire(key, time, timeUnit);
	}

	/**
	 * 给Key添加过期时间，time默认为秒
	 *
	 * @param key  redis key
	 * @param time 秒
	 * @return true 成功 false 不成功
	 */
	public Boolean expire(String key, Long time) {
		if (time < 0) {
			return false;
		}
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 根据key 获取过期时间
	 *
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效 -2为key不存在
	 */
	public Long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 根据keys删除对应key 可变参
	 *
	 * @param key 需要删除的keys
	 */
	public void del(String... key) {
		if (key != null && key.length > 0) {
			Collection<String> list = castObject(key);
			redisTemplate.delete(list);
		}
	}

	/**
	 * 判断是否存在该key
	 *
	 * @param key redis key
	 * @return true 存在 false 不存在
	 */
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获取普通String value
	 *
	 * @param key redis Key
	 * @param <T> 具体类型
	 * @return 类型结果
	 */
	public <T> T get(String key) {
		return key == null ? null : castObject(redisTemplate.opsForValue().get(key));
	}

	/**
	 * 添加普通String value
	 *
	 * @param key   redis key
	 * @param value redis value
	 * @return true of false
	 */
	public Boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加普通String value 默认分钟
	 *
	 * @param key   redis key
	 * @param value redis value
	 * @param time  过期时间
	 * @return true of false
	 */
	public Boolean set(String key, Object value, Long time) {
		try {
			redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 添加普通String value
	 *
	 * @param key      redis key
	 * @param value    redis value
	 * @param time     过期时间
	 * @param timeUnit 单位
	 * @return true of false
	 */
	public Boolean set(String key, Object value, Long time, TimeUnit timeUnit) {
		try {
			redisTemplate.opsForValue().set(key, value, time, timeUnit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几
	 */
	public void incr(String key, Integer delta) {
		try {
			redisTemplate.opsForValue().increment(key, delta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 递增 默认+1
	 *
	 * @param key 键
	 */
	public void incr(String key) {
		try {
			redisTemplate.opsForValue().increment(key, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 强转Object 防止代码检测 unchecked cast
	 *
	 * @param obj obj
	 * @param <T> 类型
	 * @return 强转result
	 */
	@SuppressWarnings("unchecked")
	public <T> T castObject(Object obj) {
		T result = null;
		try {
			result = (T) obj;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return result;
	}
}
