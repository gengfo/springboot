package com.javalsj.blog.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/** 
 * @description 用户授权信息表映射实体类(含第三方登录信息)，所有和授权相关（且基本前端展示无关的），都放在用户信息授权表，用户信息表和用户授权表是一对多的关系。
 * @author WANGJIHONG
 * @date 2018年2月27日 下午7:53:08
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 实体表结构如：
 * <br/> |id|user_id|identity_type|identifier|credential|
 * <br/> |1|1|email|123@example.com|password_hash(密码)|	
 * <br/> |2|1|phone|13888888888|password_hash(密码)|		
 * <br/> |3|1|weibo|微博UID|微博access_token|				
 * <br/> |4|2|username|moliniao|password_hash(密码)|		
 * <br/> |5|3|weixin|微信UserName|微信token|		
 */
@Entity
@Table(name="SYS_USER_AUTH")
public class SysUserAuthsPO {
	
	@Id
	@Column(length=32)
	@NotNull
	private String id;
	
	@Column(name="USER_ID")
	private Long userId;

	/**
	 * 登录类型（手机号 邮箱 用户名）或第三方应用名称（微信 微博等）
	 */
	@Column(name="IDENTITY_TYPE")
	private String identityType;
	
	/**
	 * 标识（手机号 邮箱 用户名或第三方应用的唯一标识）
	 */
	@Column(name="IDENTIFIER")
	private String identifier;
	
	/**
	 * 密码凭证（站内的保存密码，站外的不保存或保存token）
	 */
	@Column(name="CREDENTIAL")
	private String credential ;
}
