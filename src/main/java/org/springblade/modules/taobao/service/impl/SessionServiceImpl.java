package org.springblade.modules.taobao.service.impl;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.entity.BladeUser;
import org.springblade.modules.taobao.service.SessionService;
import org.springblade.modules.taobao.utils.MyRedisUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.springblade.modules.taobao.config.BashNumberInterface.REDIS_TOKEN;
import static org.springblade.modules.taobao.config.BashNumberInterface.REDIS_USER;

/**
 * 获取用户基础数据
 *
 * @author SeventySeven
 * @since 2020-08-26
 */
@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
	private final HttpServletRequest httpServletRequest;
	private final MyRedisUtil myRedisUtil;

	@Override
	public R<BladeUser> getUser() {
		String token = httpServletRequest.getHeader("token");
		String userId = myRedisUtil.get(REDIS_TOKEN+token);
		BladeUser bladeUser = myRedisUtil.get(REDIS_USER + userId);
		return R.data(bladeUser);
	}
}
