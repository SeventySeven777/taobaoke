package org.springblade.modules.taobao.utils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.springblade.modules.taobao.config.BashNumberInterface.ADMIN_ID;
import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;

/**
 * 临时解决办法,之后找到token再改
 *
 * @author SeventySeven
 * @since 2020-08-18
 */
@Component
@AllArgsConstructor
public class SaveToken {
	private RedisTemplate<String, Object> redisTemplate;

	public String getToken(String token) {
		return (String) redisTemplate.opsForValue().get(token);
	}

	public void addToken(String token, String userId) {
		redisTemplate.opsForValue().set(token, userId, 30, TimeUnit.MINUTES);
	}
}
