package org.springblade.test;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

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
		String s = null;
		String s1 = "";
		String s2 = " ";
		String s3 = "1";
		System.out.println(StrUtil.isBlank(s));
		System.out.println(StrUtil.isBlank(s1));
		System.out.println(StrUtil.isBlank(s2));
		System.out.println(StrUtil.isBlank(s3));
	}
}
