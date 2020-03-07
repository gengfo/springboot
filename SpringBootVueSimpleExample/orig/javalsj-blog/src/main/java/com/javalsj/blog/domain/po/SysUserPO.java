package com.javalsj.blog.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/** 
 * @description 用户基础信息表映射持久化对象,用户信息表不保存任何密码，不保存任何登录信息（如用户名、手机号、邮箱），只留有昵称、头像等基础信息。
 * @author WANGJIHONG
 * @date 2018年2月27日 下午7:22:19 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 表结构如下：
 * <br/> |id|nickname|avatar| 					
 * <br/> |1|小王|http://…/avatar.jpg|			
 * <br/> |2|大王|http://…/avatar2.jpg|				
 * <br/> |3|老王|http://…/avatar3.jpg|		
 */
@Entity
@Table(name="SYS_USER")
@Data
public class SysUserPO {
	
	/**
	 * 主键
	 */
	@Id()
	@Column(length=32, name="ID")
	private String id;
	
	/**
	 * 用户名
	 */
	@Column(length = 24, name="USERNAME")
	private String username;
	
	/**
	 * 密码
	 */
	@Column(length = 32, name="PASSWORD")
	private String password;

	/**
	 * 昵称
	 */
	@Column(length = 30, name="NICKNAME")
	private String nickName;

}
