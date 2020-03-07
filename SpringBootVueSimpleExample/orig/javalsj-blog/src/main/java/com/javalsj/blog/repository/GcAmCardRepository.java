package com.javalsj.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javalsj.blog.domain.po.GcAmCardPO;

/**
 * @description 固定资产台账村储层
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:17:12 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Repository
public interface GcAmCardRepository extends JpaRepository<GcAmCardPO, String> {
	
	/** 
	 * 根据名称删除数据
	 * @param cardname 固定资产台账名称
	 * @return void
	 */ 
	@Modifying
	@Query("delete from GcAmCardPO as p where p.name = ?1")
	void deleteByCardName(String cardname);
	
}