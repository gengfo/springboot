package com.javalsj.blog.domain.vo;

import lombok.Data;

/** 
 * @description 用户基础信息数据显示层对象
 * @author WANGJIHONG
 * @date 2018年2月28日 下午9:29:51
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
@Data
public class SysUserVO {
	
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 头像
	 */
	private String avatar;

}
