package com.javalsj.blog.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javalsj.blog.autoconfigure.jwt.JwtUtil;
import com.javalsj.blog.common.UUIDUtil;
import com.javalsj.blog.domain.dto.SysUserDTO;
import com.javalsj.blog.domain.vo.LoginRegisterInfoVo;
import com.javalsj.blog.domain.vo.SysUserVO;
import com.javalsj.blog.result.Result;
import com.javalsj.blog.result.ResultFactory;
import com.javalsj.blog.service.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/** 
 * @description 登录注册控制器
 * @author WANGJIHONG
 * @date 2018年5月5日 下午7:39:06 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
@Api(value = "LoginRegister", description = "用户登录注册管理")
@RestController
@CrossOrigin
public class LoginRegisterController {
	
	/*@Autowired
	private RedisService<RedisUser> redisService;*/
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 注册控制器
	 */
	@PostMapping(value = "/api/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Result register(@Valid @RequestBody LoginRegisterInfoVo loginRegisterInfoVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String message = String.format("注册失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}
		SysUserDTO sysUserDTO = sysUserService.getOneByUsername(loginRegisterInfoVo.getUsername());
		if (sysUserDTO != null) {
			String message = String.format("注册失败，详细信息[用户名“%s”已存在]。", loginRegisterInfoVo.getUsername());
			return ResultFactory.buildFailResult(message);
		}
		SysUserDTO userDTO = new SysUserDTO();
		userDTO.setId(UUIDUtil.newUUID());
		userDTO.setPassword(loginRegisterInfoVo.getPassword());
		userDTO.setUsername(loginRegisterInfoVo.getUsername());
		try {
			sysUserService.save(userDTO);
		} catch (Exception e) {
			e.printStackTrace();
			String message = String.format("注册失败，详细信息[%s]。", e.getMessage());
			return ResultFactory.buildFailResult(message);
		}
		return ResultFactory.buildSuccessResult("注册成功。");
	}
	
	/**
	 * 登录控制器
	 * 前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
	 * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
	 * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
	 */
	@PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Result login(@Valid @RequestBody LoginRegisterInfoVo loginRegisterInfoVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String message = String.format("登录失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}
		SysUserDTO sysUserDTO = sysUserService.getOneByUsername(loginRegisterInfoVo.getUsername());
		if (sysUserDTO == null) {
			String message = String.format("登陆失败，详细信息[用户名“%s”不存在]。", loginRegisterInfoVo.getUsername());
			return ResultFactory.buildFailResult(message);
		}
		if (loginRegisterInfoVo.isAdmin() && !sysUserDTO.isHasAdminAuth()) {
			String message = String.format("登陆失败，详细信息[用户名“%s”不是管理员，不允许登录管理平台]。", loginRegisterInfoVo.getUsername());
			return ResultFactory.buildFailResult(message);
		}
		if (!Objects.equals(sysUserDTO.getUsername(), loginRegisterInfoVo.getUsername()) || !Objects.equals(sysUserDTO.getPassword(), loginRegisterInfoVo.getPassword())) {
			return ResultFactory.buildFailResult("登陆失败，详细信息[用户名、密码信息不正确]。");
		}
		// 返回Token
		String jwtToken = JwtUtil.generateToken(loginRegisterInfoVo.getUsername());
		return ResultFactory.buildSuccessResult(jwtToken, "登录成功");
	}
	
	@ApiOperation(value = "用户基本信息API", notes = "获取全部用户基本信息")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "name", value = "用户名称", dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页记录条数", dataType = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码", dataType = "Integer") })
	@GetMapping(value = "/api/user/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result list(@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "pageNum") Integer pageNum) {
		Page<SysUserDTO> sysUserDTOPage = sysUserService.findAll(pageSize, pageNum, null);
		List<SysUserDTO> sysUserDTOs = sysUserDTOPage.getContent();
		List<SysUserVO> sysUserVOs = sysUserDTOs.stream()
				.map(sysUserDTO2 -> sysUserService.convertSysUserDTO2VO(sysUserDTO2)).collect(Collectors.toList());
		Result result = ResultFactory.buildSuccessResult(sysUserVOs);
		return result;
	}

}
