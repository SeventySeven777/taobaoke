package org.springblade.modules.taobao.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.springblade.modules.taobao.config.MethodConfig.DECODE_ERROR;

/**
 * 应对支付宝小程序那狗日的传过来的参数是乱码的问题
 * 前端对所有中文参数进行编码 后端接收参数后再次解码
 *
 * @author SeventySeven
 * @since 2020-08-20
 */
public class DoDecodeAliPayCode {
	public static String deCode(String s) {
		String decode = DECODE_ERROR;
		try {
			decode = URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}
}
