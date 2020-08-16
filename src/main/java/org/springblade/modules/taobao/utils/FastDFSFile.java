package org.springblade.modules.taobao.utils;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件上传
 * @author SeventySeven
 * @since 2019-02-8
 */
@ApiModel(value = "文件上传")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FastDFSFile {
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件内容
	 */
	private byte[] content;
	/**
	 * 文件扩展名
	 */
	private String ext;
	/**
	 * 文件MD5摘要值
	 */
	private String md5;
	/**
	 * 文件创建作者
	 */
	private String author;

	public FastDFSFile(String name, byte[] content, String ext, String height,
					   String width, String author) {
		super();
		this.name = name;
		this.content = content;
		this.ext = ext;
		this.author = author;
	}

	public FastDFSFile(String name, byte[] content, String ext) {
		super();
		this.name = name;
		this.content = content;
		this.ext = ext;
	}

}
