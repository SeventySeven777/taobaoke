package org.springblade.modules.taobao.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static org.springblade.modules.taobao.config.MethodConfig.LOGIN_PLEASE;
import static org.springblade.modules.taobao.config.TaobaoURLConfig.*;

/**
 * 实在找不到他token在哪验证的
 *
 * @author SventySeven
 * @since 2020-08-18
 */
@WebFilter(urlPatterns = {BLADE_ORDER_URL + "/*", BLADE_WALLET_HISTORY_URL + "/*", BLADE_WALLET_URL + "/*", BLADE_USER_STORE_URL + "/*", BLADE_ORDER_URL + "/*",
	BLADE_USER_URL + "/*", BLADE_USER_CHECK_URL + "/*", BLADE_USER_BASH_URL + "/*", BLADE_STORE_USER_MIDDLE_URL + "/*", BLADE_RATE_URL + "/*", BLADE_ORDER_URL + "/*"})
public class TokenFilter implements Filter {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse rep = (HttpServletResponse) servletResponse;
		String token = req.getHeader("token");
		String userId = new SaveToken(redisTemplate).getToken(token);
		if (null == userId || StrUtil.isEmpty(userId)) {
			rep.setStatus(401);
			PrintWriter writer = null;
			OutputStreamWriter osw = null;
			osw = new OutputStreamWriter(rep.getOutputStream(),
				"UTF-8");
			writer = new PrintWriter(osw, true);
			R<Object> fail = R.fail(LOGIN_PLEASE);
			String jsonStr = JSONUtil.toJsonStr(fail);
			writer.write(jsonStr);
			writer.flush();
			writer.close();
			osw.close();
			return;
		}
		filterChain.doFilter(req, rep);
	}

	@Override
	public void destroy() {

	}
}
