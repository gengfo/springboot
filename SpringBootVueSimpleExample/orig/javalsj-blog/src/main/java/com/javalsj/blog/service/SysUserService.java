package com.javalsj.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.javalsj.blog.domain.dto.SysUserDTO;
import com.javalsj.blog.domain.po.SysUserPO;
import com.javalsj.blog.domain.vo.SysUserVO;

/**
 * @description 用户基础信息服务接口
 * @author WANGJIHONG
 * @date 2018年2月27日 下午9:29:39
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
public interface SysUserService {

	/**
	 * 保存或更新用户基本信息
	 * @param sysUserDTO 用户基本信息传输对象
	 * @param dataSourceType 数据源
	 * @return SysUserDTO 保存或更新用户基本信息后的数据传输对象
	 */
	SysUserDTO save(SysUserDTO sysUserDTO);
	
	/**
	 * 根据id删除数据
	 * @param id 用户基本信息id
	 */
	void deleteById(String id);
	
	/**
	 * 根据ID查询单个用户
	 * @param userId 用户Id
	 * @param dataSourceType 数据源
	 * @return SysUserDTO 返回用户基本信息数据传输对象
	 */
	SysUserDTO getOne(String userId);
	
	/**
	 * 根据用户名查询单个用户
	 * @param username 用户名
	 * @return SysUserDTO 返回用户基本信息数据传输对象
	 */
	SysUserDTO getOneByUsername(String username);

	/**
	 * 查询用户总数
	 * @param dataSourceType 数据源
	 * @return 返回用户基本信息总数量
	 */
	long count();
	
	/**
	 * 分页查询,按size分页后，查询第page页的数据 
	 * @param pageSize 每页显示的记录数，  为0或null则不分页查询所有
	 * @param pageNum：当前页为第几页，从0开始，默认为第0页, 为null则不分页查询所有
	 * @param sort 排序相关的信息，以property,property(ASC|DESC)的方式组织，支持多字段排序，如：Sort sort = new Sort(Direction.DESC, "id");
	 * @param dataSourceType 数据源
	 * @return Page<SysUserDTO> 分页后结果集
	 */
	Page<SysUserDTO> findAll(Integer pageSize, Integer pageNum, Sort sort);
	
	/**
	 * 用户持久化对象转化成传输数据对象
	 * @param sysUserPO 用户基本信息持久化对象
	 * @return SysUserDTO 返回用户基本信息数据传输对象
	 */
	SysUserDTO convertSysUserPO2DTO(SysUserPO sysUserPO);
	
	/**
	 * 用户传输数据对象转化成持久化对象
	 * @param sysUserDTO 用户基本信息数据传输对象
	 * @return SysUserPO 用户基本信息持久化对象
	 */
	SysUserPO convertSysUserDTO2PO(SysUserDTO sysUserDTO);
	
	/**
	 * 用户传输数据对象转化成视图显示对象
	 * @param sysUserDTO 用户基本信息数据传输对象
	 * @return SysUserVO 用户基本信息视图显示对象
	 */
	SysUserVO convertSysUserDTO2VO(SysUserDTO sysUserDTO);
	
}
