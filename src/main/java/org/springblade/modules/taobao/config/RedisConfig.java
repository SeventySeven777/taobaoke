package org.springblade.modules.taobao.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author SeventySeven
 */
@Configuration
public class RedisConfig {
	/**
	 * 把任何数据保存到redis时，都需要进行序列化，默认使用JdkSerializationRedisSerializer进行序列化。
	 * 默认的序列化会给所有的key,value的原始字符前，都加了一串字符（例如：\xAC\xED\x00\），不具备可读性
	 * 所以需要配置jackson序列化方式
	 */

}
