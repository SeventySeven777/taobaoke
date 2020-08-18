package org.springblade.modules.taobao.utils;

import java.util.concurrent.ConcurrentHashMap;

import static org.springblade.modules.taobao.config.MethodConfig.SAVE_OK;

/**
 * 临时解决办法,之后找到token再改
 *
 * @author SeventySeven
 * @since 2020-08-18
 */
public class SaveToken {
	private static ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>(1 << 15);

	public static ConcurrentHashMap<String, String> getTokenMap() {
		return tokenMap;
	}

	public static void deleteTokenMap() {
		tokenMap = new ConcurrentHashMap<>(1 << 15);
	}

	public static void addToken(String token) {
		tokenMap.put(token, SAVE_OK);
	}
}
