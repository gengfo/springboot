package com.javalsj.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javalsj.blog.domain.dto.GcAmCardDTO;
import com.javalsj.blog.domain.po.GcAmCardPO;
import com.javalsj.blog.domain.vo.GcAmCardVo;
import com.javalsj.blog.repository.GcAmCardRepository;
import com.javalsj.blog.service.GcAmCardService;

/**
 * @description 固定资产台账服务层
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:34:47 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Service
public class GcAmCardServiceImpl implements GcAmCardService{
	@Autowired
	private GcAmCardRepository gcAmCardRepository;

	@Transactional(timeout=10, rollbackFor=Exception.class)
	@Override
	public GcAmCardDTO save(GcAmCardDTO gcAmCardDTO) {
		GcAmCardPO gcAmCardPO = convertDTO2PO(gcAmCardDTO);
		gcAmCardRepository.save(gcAmCardPO);
		return gcAmCardDTO;
	}
	
	@Transactional(timeout=10, rollbackFor=Exception.class)
	@Override
	public void deleteByCardName(String cardname) {
		gcAmCardRepository.deleteByCardName(cardname);
	} 
	
	@Override
	public Page<GcAmCardDTO> findAll(Integer pageSize, Integer pageNum, Sort sort) {
		List<GcAmCardPO> amCardPOs;
		if (pageSize == null || pageSize == 0 || pageNum == null) {
			amCardPOs = gcAmCardRepository.findAll();
		} else {
			Pageable pageable = PageRequest.of(pageNum, pageSize); 
			amCardPOs = gcAmCardRepository.findAll(pageable).getContent();
		}
		List<GcAmCardDTO> content = amCardPOs.stream().map(amCardPO -> convertPO2DTO(amCardPO)).collect(Collectors.toList());;
		Page<GcAmCardDTO> page = new PageImpl<>(content);
		return page;
	}
	
	@Override
	public GcAmCardDTO convertPO2DTO(GcAmCardPO amCardPO) {
		GcAmCardDTO gcAmCardDTO= new GcAmCardDTO();
		BeanUtils.copyProperties(amCardPO, gcAmCardDTO);
		return gcAmCardDTO;
	}
	
	@Override
	public GcAmCardPO convertDTO2PO(GcAmCardDTO amCardDTO) {
		GcAmCardPO gcAmCardPO = new GcAmCardPO();
		BeanUtils.copyProperties(amCardDTO, gcAmCardPO);
		return gcAmCardPO;
	}

	@Override
	public GcAmCardVo convertDTO2VO(GcAmCardDTO amCardDTO) {
		GcAmCardVo gcAmCardVo = new GcAmCardVo();
		BeanUtils.copyProperties(amCardDTO, gcAmCardVo);
		return gcAmCardVo;
	}

	@Override
	public GcAmCardDTO convertVO2DTO(GcAmCardVo amCardVo) {
		GcAmCardDTO gcAmCardDTO= new GcAmCardDTO();
		BeanUtils.copyProperties(amCardVo, gcAmCardDTO);
		return gcAmCardDTO;
	}
	
}
