package com.javalsj.blog.common;

import java.io.File;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 文件工具类
 * @author WANGJIHONG
 * @date 2018年3月14日 下午8:59:57
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
	private static final int FILE_ONE_KB_SIZE = 1024;

	/**
	 * 文件大小的格式化
	 * 
	 * @param size
	 *            文件大小，单位为byte
	 * @return 文件大小格式化后的文本
	 */
	public static String formatSize(long size) {
		DecimalFormat df = new DecimalFormat("#,###.00");
		if (size < FILE_ONE_KB_SIZE) {
			// 小于1KB
			return size + "Byte";
		} else if (size < FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE) {
			// 小于1MB
			float kSize = (size / FILE_ONE_KB_SIZE) * 1.0f;
			return df.format(kSize) + "KB";
		} else if (size < FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE) {
			// 小于1GB
			float mSize = (size / FILE_ONE_KB_SIZE / FILE_ONE_KB_SIZE) * 1.0f;
			return df.format(mSize) + "MB";
		} else if (size < FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE * FILE_ONE_KB_SIZE) {
			// 小于1TB
			float gSize = (size / FILE_ONE_KB_SIZE / FILE_ONE_KB_SIZE / FILE_ONE_KB_SIZE) * 1.0f;
			return df.format(gSize) + "GB";
		} else {
			return "size: error";
		}
	}

	/**
	 * 获取文件、文件夹的总大小
	 */
	public static long getDirSize(File file) {
		if (file == null) {
			return 0;
		}
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				long size = 0;
				for (File f : children) {
					size += getDirSize(f);
				}
				return size;
			} else {
				return file.length();
			}
		} else {
			LOGGER.error("文件或者文件夹{}不存在，请检查路径是否正确！", file.getName());
			return 0;
		}
	}
}
