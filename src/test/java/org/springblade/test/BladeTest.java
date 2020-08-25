package org.springblade.test;

import cn.hutool.core.lang.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springblade.modules.desk.service.INoticeService;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.utils.MyRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@RunWith(BladeSpringRunner.class)
@BladeBootTest(appName = "blade-runner", profile = "test")
public class BladeTest {

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private MyRedisUtil redisUtil;

	@Test
	public void contextLoads() {
//		BladeUser bladeUser = new BladeUser().setPassword("123").setPhone("4562161").setCreateDate(new Date()).setStatus(2).setRole(1).setId(UUID.randomUUID().toString());
//		Map<String, BladeUser> map = new HashMap<>();
//		map.put("one", bladeUser);
//		map.put("one", bladeUser);
//		List<Map<String, BladeUser>> list = new ArrayList<>();
//		list.add(map);
//		list.add(map);
//		redisUtil.set("1", list);
//		List<Map<String, BladeUser>> list2 = redisUtil.get("1");
//		System.out.println(list2.get(0).get("one").getId());
//		System.out.println(list2);
		System.out.println(redisTemplate.hasKey("1234"));
		System.out.println(redisTemplate.expire("123123", 12123, TimeUnit.DAYS));
		System.out.println(redisTemplate.getExpire("12312sd", TimeUnit.SECONDS));
	}

}
