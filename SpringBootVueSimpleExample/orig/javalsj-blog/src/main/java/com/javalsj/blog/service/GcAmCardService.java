package com.javalsj.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.javalsj.blog.domain.dto.GcAmCardDTO;
import com.javalsj.blog.domain.po.GcAmCardPO;
import com.javalsj.blog.domain.vo.GcAmCardVo;

/**
 * @description 固定资产台账服务层接口
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:32:43 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public interface GcAmCardService {
	
	/**
	 * 保存或更新固定资产台账信息
	 * @param gcAmCardDTO 固定资产台账信息传输对象
	 * @return GcAmCardDTO 保存或更新固定资产台账信息后的数据传输对象
	 */
	GcAmCardDTO save(GcAmCardDTO gcAmCardDTO);
	
	/**
	 * 根据资产卡片名称删除数据
	 * @param cardName 卡片名称
	 */
	void deleteByCardName(String cardName);
	
	/**
	 * 分页查询,按size分页后，查询第page页的数据 
	 * @param pageSize 每页显示的记录数，  为0或null则不分页查询所有
	 * @param pageNum：当前页为第几页，从0开始，默认为第0页, 为null则不分页查询所有
	 * @param sort 排序相关的信息，以property,property(ASC|DESC)的方式组织，支持多字段排序，如：Sort sort = new Sort(Direction.DESC, "id");
	 * @param dataSourceType 数据源
	 * @return Page<SysUserDTO> 分页后结果集
	 */
	Page<GcAmCardDTO> findAll(Integer pageSize, Integer pageNum, Sort sort);
	
	/**
	 * 固定资产台账持久化对象转化成传输数据对象
	 * @param amCardPO 固定资产台账持久化对象
	 * @return GcAmCardDTO 返回固定资产台账信息数据传输对象
	 */
	GcAmCardDTO convertPO2DTO(GcAmCardPO amCardPO);
	
	/**
	 * 固定资产台账传输数据对象转化成持久化对象
	 * @param amCardDTO 固定资产台账数据传输对象
	 * @return GcAmCardPO 固定资产台账持久化对象
	 */
	GcAmCardPO convertDTO2PO(GcAmCardDTO amCardDTO);
	
	/**
	 * 固定资产台账传输数据对象转化成视图显示对象
	 * @param amCardDTO 固定资产台账数据传输对象
	 * @return GcAmCardVo 固定资产台账视图显示对象
	 */
	GcAmCardVo convertDTO2VO(GcAmCardDTO amCardDTO);
	
	/**
	 * 固定资产台账视图显示对象转化成传输数据对象
	 * @param amCardVo 固定资产台账视图显示对象
	 * @return GcAmCardDTO 固定资产台账数据传输对象
	 */
	GcAmCardDTO convertVO2DTO(GcAmCardVo amCardVo);

}
