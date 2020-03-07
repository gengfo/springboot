package com.javalsj.blog.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javalsj.blog.domain.dto.GcAmCardDTO;
import com.javalsj.blog.domain.vo.GcAmCardVo;
import com.javalsj.blog.result.Result;
import com.javalsj.blog.result.ResultFactory;
import com.javalsj.blog.service.GcAmCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description 固定资产台账管理demo控制器
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:10:26 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Api(value = "GcAmCard", description = "固定资产台账管理")
@CrossOrigin
@Controller
@RequestMapping("/api/gc/amcard")
public class GcAmCardController {

	@Autowired
	private GcAmCardService gcAmCardService;

	@ApiOperation(value = "固定资产台账API", notes = "新增信息")
	@RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Result addGcAmCard(@Valid @RequestBody GcAmCardVo gcAmCardVo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String message = String.format("合并固定资产台账新增失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
			return ResultFactory.buildFailResult(message);
		}
		try {
			GcAmCardDTO amCardDTO = gcAmCardService.convertVO2DTO(gcAmCardVo);
			gcAmCardService.save(amCardDTO);
		} catch (Exception e) {
			String message = String.format("合并固定资产台账新增失败，详细信息[%s]。", e.getMessage());
			return ResultFactory.buildFailResult(message);
		}
		return ResultFactory.buildSuccessResult("新增成功");
	}

	@ApiOperation(value = "固定资产台账API", notes = "根据资产台账名称删除信息")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "cardName", value = "资产台账名称", dataType = "String") })
	@DeleteMapping(value = "deleteByCardName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Result deleteByCardName(@RequestParam(value = "cardName") String cardName) {
		if (StringUtils.isEmpty(cardName)) {
			String message = String.format("合并固定资产台账删除失败，详细信息[%s]。", "资产台账名称参数不允许为空");
			return ResultFactory.buildFailResult(message);
		}
		try {
			gcAmCardService.deleteByCardName(cardName);
		} catch (Exception e) {
			String message = String.format("合并固定资产台账删除失败，详细信息[%s]。", e.getMessage());
			return ResultFactory.buildFailResult(message);
		}
		return ResultFactory.buildSuccessResult("删除成功");
	}
	
	@ApiOperation(value = "固定资产台账API", notes = "获取资产台账信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页记录条数", dataType = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码", dataType = "Integer") })
	@GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Result list(@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "pageNum") Integer pageNum) {
		Page<GcAmCardDTO> gcAmCardDTOPage = gcAmCardService.findAll(pageSize, pageNum, null);
		List<GcAmCardDTO> gcAmCardDTOs = gcAmCardDTOPage.getContent();
		List<GcAmCardVo> gcAmCardVos = gcAmCardDTOs.stream()
				.map(amCardDTO -> gcAmCardService.convertDTO2VO(amCardDTO)).collect(Collectors.toList());
		Result result = ResultFactory.buildSuccessResult(gcAmCardVos);
		return result;
	}

}
