package com.javalsj.blog.domain.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @description Vue登录注册页面信息对象实体
 * @author WANGJIHONG
 * @date 2018年5月5日 下午5:57:53
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
@Data
public class LoginRegisterInfoVo {

	/**
	 * 是否为注册信息（true:注册，false:登录）
	 */
	private boolean isRegister;

	@NotNull(message = "用户名不允许为空")
	@Size(min = 6, max = 24, message = "用户名长度应该在6和24位之间")
	private String username;

	@NotNull(message = "密码不允许为空")
	@Size(min = 6, max = 24, message = "密码长度应该在6和24位之间")
	private String password;

	/**
	 * 确认密码（注册）
	 */
	private String password2;

	/**
	 * 验证码
	 */
	private String verifyCode;

	/**
	 * 是否管理平台登录
	 */
	private boolean isAdmin;

}
