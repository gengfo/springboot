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

import com.javalsj.blog.autoconfigure.datasource.MultiTargetDataSource;
import com.javalsj.blog.domain.dto.SysUserDTO;
import com.javalsj.blog.domain.po.SysUserPO;
import com.javalsj.blog.domain.vo.SysUserVO;
import com.javalsj.blog.repository.SysUserRepository;
import com.javalsj.blog.service.SysUserService;

/** 
 * @description 用户基础信息服务层
 * @author WANGJIHONG
 * @date 2018年2月27日 下午9:33:02
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注信息
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserRepository sysUserRepository;
	
	@Override
	@MultiTargetDataSource(name = "default")
	public SysUserDTO save(SysUserDTO sysUserDTO) {
		SysUserPO sysUserPO = convertSysUserDTO2PO(sysUserDTO);
		sysUserRepository.save(sysUserPO);
		return sysUserDTO;
	}

	@Override
	@MultiTargetDataSource(name = "second")
	public void deleteById(String id) {
		sysUserRepository.deleteById(id);
	}
	
	@Override
	public SysUserDTO getOne(String userId) {
		SysUserPO sysUserPO = sysUserRepository.getOne(userId);
		if (sysUserPO == null) {
			return null;
		} else {
			SysUserDTO sysUserDTO = convertSysUserPO2DTO(sysUserPO);
			return sysUserDTO;
		}
	}
	
	@Override
	public SysUserDTO getOneByUsername(String username) {
		SysUserPO sysUserPO = sysUserRepository.findByUsername(username);
		if (sysUserPO == null) {
			return null;
		} else {
			SysUserDTO sysUserDTO = convertSysUserPO2DTO(sysUserPO);
			return sysUserDTO;
		}
	}
	
	@Override
	public long count(){
		long count = sysUserRepository.count();
		return count;
	}
	
	@Override
	@MultiTargetDataSource(name="third")
	public Page<SysUserDTO> findAll(Integer pageSize, Integer pageNum, Sort sort) {
		List<SysUserPO> sysUserPOs;
		if (pageSize == null || pageSize == 0 || pageNum == null) {
			sysUserPOs = sysUserRepository.findAll();
		} else {
			Pageable pageable = PageRequest.of(pageNum, pageSize); 
			sysUserPOs = sysUserRepository.findAll(pageable).getContent();
		}
		List<SysUserDTO> content = sysUserPOs.stream().map(sysUserPO -> convertSysUserPO2DTO(sysUserPO)).collect(Collectors.toList());;
		Page<SysUserDTO> sysUserDTOPage = new PageImpl<>(content);
		return sysUserDTOPage;
	}
	
	@Override
	public SysUserPO convertSysUserDTO2PO(SysUserDTO sysUserDTO) {
		SysUserPO sysUserPO = new SysUserPO();
		BeanUtils.copyProperties(sysUserDTO, sysUserPO);
		return sysUserPO;
	}
	
	@Override
	public SysUserDTO convertSysUserPO2DTO(SysUserPO sysUserPO) {
		SysUserDTO sysUserDTO = new SysUserDTO();
		BeanUtils.copyProperties(sysUserPO, sysUserDTO);
		return sysUserDTO;
	}
	
	@Override
	public SysUserVO convertSysUserDTO2VO(SysUserDTO sysUserDTO) {
		SysUserVO sysUserVO = new SysUserVO();
		BeanUtils.copyProperties(sysUserDTO, sysUserVO);
		return sysUserVO;
	}
}
