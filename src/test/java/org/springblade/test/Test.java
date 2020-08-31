package org.springblade.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.Data;
import org.springblade.modules.taobao.utils.DoDecodeAliPayCode;

import java.net.URLDecoder;
import java.nio.charset.Charset;
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
		System.out.println(new Date().getTime() - (new Date().getTime() + 1));
		System.out.println(DateUtil.beginOfMonth(new Date()));
		System.out.println(DateUtil.endOfMonth(new Date()));
//
//		String s = "";
//		String s1 ="1";
//		System.out.println(!StrUtil.isEmpty(s));
//		System.out.println(!StrUtil.isEmpty(s1));
//		System.out.println(String.format("%04d", 12));
//
//		A as = new Test().new A();
//		as.setUserName("ad");
//		String encode = URLEncoder.createAll().encode("section class=\\\"_135editor\\\" data-role=\\\"paragraph\\\">\\n    <p>\\n        <br/>\\n &nbsp; &nbsp;\\n    </p >\\n</section>\\n<section class=\\\"_135editor\\\" data-tools=\\\"135编辑器\\\" data-id=\\\"91986\\\">\\n    <section style=\\\"margin: 10px auto;\\\">\\n        <section style=\\\"width: 100%;background: -webkit-linear-gradient(top,#fefefe,#f3fcee);color: #333;overflow: hidden;\\\" data-width=\\\"100%\\\">\\n            <section style=\\\"width: 100%;background-image: url(https://bdn.135editor.com/files/images/editor_styles/95756ab0cd13a7a4f749d78a056eaadf.jpg);background-position: bottom;\\\" data-width=\\\"100%\\\">\\n                <section style=\\\"width:40px;\\\">\\n                    < img style=\\\"width:40px; display:block;\\\" src=\\\"https://bdn.135editor.com/files/images/editor_styles/d6d05a93ed731139ee1405a5806292be.jpg\\\" data-ratio=\\\"1.1232394366197183\\\" data-w=\\\"284\\\"/>\\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\\n                </section>\\n                <section style=\\\"padding:15px;line-height: 28px;text-align: justify;\\\">\\n                    <section data-autoskip=\\\"1\\\" class=\\\"135brush\\\" style=\\\"text-align: justify;line-height:1.75em;letter-spacing: 1.5px;font-size:14px;color:#333;\\\">\\n                        <p>\\n                            你好，我们是你亲友为你点的七夕祝福和平鸽和祝福青蛙组合，现在我们要开始叫了:孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡孤寡...\\n                        </p >\\n                    </section>\\n                </section>\\n                <section style=\\\"width:50px;float: right;\\\">\\n                    < img style=\\\"width:50px; display:block;\\\" src=\\\"https://bdn.135editor.com/files/images/editor_styles/d6d05a93ed731139ee1405a5806292be.jpg\\\" data-ratio=\\\"1.1232394366197183\\\" data-w=\\\"284\\\"/>\\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\\n                </section>\\n            </section>\\n        </section>\\n    </section>\\n</section>", Charset.defaultCharset());
//		System.out.println(encode);
	}
//	@Data
//	public class A{
//		private String userName;
//	}


}
