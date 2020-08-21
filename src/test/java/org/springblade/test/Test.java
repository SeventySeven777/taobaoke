package org.springblade.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import org.springblade.modules.taobao.utils.DoDecodeAliPayCode;

import java.net.URLDecoder;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
//		System.out.println(UUID.randomUUID().toString());
//		System.out.println(UUID.randomUUID().toString());
//		System.out.println(UUID.randomUUID().toString());
//		String s = "123456";
//		System.out.println(SecureUtil.md5(s));
//		System.out.println(SecureUtil.md5("123456"));
//		System.out.println(SecureUtil.md5("123456"));
//		System.out.println(SecureUtil.md5("123456"));
//		String s = null;
//		String s1 = "";
//		String s2 = " ";
//		String s3 = "1";
//		System.out.println(StrUtil.isBlank(s));
//		System.out.println(StrUtil.isBlank(s1));
//		System.out.println(StrUtil.isBlank(s2));
//		System.out.println(StrUtil.isBlank(s3));
//		String s = "中国asd213guo国家";
//		System.out.println(DoDecodeAliPayCode.deCode(s));
//		String encode = URLUtil.encode(s);
//		System.out.println(URLUtil.encode(s));
//		System.out.println(DoDecodeAliPayCode.deCode(encode));
//		System.out.println(new Date().getTime() - (new Date().getTime() + 1));
		System.out.println(DateUtil.beginOfMonth(new Date()));
		System.out.println(DateUtil.endOfMonth(new Date()));

		String s = "";
		String s1 ="1";
		System.out.println(!StrUtil.isEmpty(s));
		System.out.println(!StrUtil.isEmpty(s1));


	}
}
