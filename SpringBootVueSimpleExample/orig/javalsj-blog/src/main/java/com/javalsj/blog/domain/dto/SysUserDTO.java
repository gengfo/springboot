package com.javalsj.blog.domain.dto;

import lombok.Data;

/** 
 * @description 用户基础信息数据传输对象
 * @author WANGJIHONG
 * @date 2018年2月27日 下午7:22:19
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
@Data
public class SysUserDTO {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickName;
	
	/** 
	 * 是否拥有管理员权限
	 */ 
	private boolean hasAdminAuth;

}
