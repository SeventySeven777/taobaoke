package org.springblade.modules.taobao.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springblade.core.tool.api.R;
import org.springblade.modules.taobao.utils.FastDFSClient;
import org.springblade.modules.taobao.utils.FastDFSFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springblade.modules.taobao.config.TaobaoURLConfig.FILE_LOAD_URL;

/**
 * 传文件
 *
 * @author SventySeven
 */
@RestController
@RequestMapping(FILE_LOAD_URL)
@Api(value = "上传文件", tags = "上传文件")
public class FileLoadController {
	/**
	 * 场景:上传任何文件
	 * 文件返回的是下载地址 图片会被浏览器解析成直接显示
	 *
	 * @param file
	 * @return 返回的为储存地址URL : IP+GROUP+file路径
	 */
	@PostMapping("")
	@ApiOperation(value = "上传图片使用", tags = "上传图片使用")
	public R uploadFile(@RequestBody MultipartFile file) {
		try {
			if (file == null) {
				throw new RuntimeException("空文件或文件不存在");
			}
			String originalFilename = file.getOriginalFilename();
			if (StringUtils.isEmpty(originalFilename)) {
				throw new RuntimeException("文件名不存在");
			}
			String exeName = originalFilename.substring(originalFilename.indexOf(".") + 1);
			byte[] bytes = file.getBytes();
			FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, bytes, exeName);
			String[] upload = FastDFSClient.upload(fastDFSFile);
			String url = FastDFSClient.getTrackerUrl() + upload[0] + "/" + upload[1];
			Map<String, String> map = new HashMap<>();
			map.put("url", url);
			map.put("fileName", upload[1]);
			map.put("group", upload[0]);
			return R.data(map, "上传成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.fail("上传失败");
	}

	/**
	 * 场景:下载通过上方存储的图片
	 * groupName 为URL 第一段
	 * fileName 需要去掉最前面的/
	 *
	 * @param fileName
	 * @return
	 */
	@GetMapping("/downLoad")
	@ApiOperation(value = "下载图片使用", tags = "下载图片使用")
	public Object downloadFile(@RequestParam("fileName") String fileName) {
		List<String> list = getFileName(fileName);
		InputStream inputStream = FastDFSClient.downFile(list.get(0), list.get(1));
		try {
			byte[] body = new byte[inputStream.available()];
			inputStream.read(body);
			String[] split = fileName.split("/");
			fileName = split[split.length - 1];
			fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
			//防止中文乱码
			HttpHeaders headers = new HttpHeaders();
			//设置响应头
			headers.add("Content-Disposition", "attachment;filename=" + fileName);
			HttpStatus statusCode = HttpStatus.OK;
			//设置响应吗
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "文件下载失败";
	}

	/**
	 * 解析URL
	 * 看起来参数简单点
	 *
	 * @param url
	 * @return
	 */
	private List<String> getFileName(String url) {
		String[] split = url.split("/");
		System.out.println(split.length);
		for (String s : split) {
			System.out.println(s);
		}
		List<String> list = new ArrayList<>();
		list.add(split[3]);
		list.add(split[4] + "/" + split[5] + "/" + split[6] + "/" + split[7]);
		return list;
	}
}
