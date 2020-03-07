package com.javalsj.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javalsj.blog.domain.po.SysUserPO;

/**
 * @description 用户存储数据访问组件
 * @author WANGJIHONG
 * @date 2018年3月5日 下午8:36:46 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUserPO, String> {
	
	/** 
	 * 根据用户名查询用户
	 * @param username 用户名
	 * @return SysUserPO 用户对象
	 */ 
	SysUserPO findByUsername(String username);
	
}
