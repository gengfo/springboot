package com.javalsj.blog.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javalsj.blog.autoconfigure.mail.JavaMailService;
import com.javalsj.blog.result.Result;
import com.javalsj.blog.result.ResultFactory;

import io.swagger.annotations.Api;

/** 
 * @description 邮件控制器
 * @author WANGJIHONG
 * @date 2018年5月5日 下午7:36:59 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 */
@Api(value = "user", description = "用户管理")
@Controller
public class EmailController {
	
	@Autowired
	private JavaMailService javaMailUtil;
	
	@RequestMapping(value = "/sendSimpleEmail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result sendSimpleEmail() {
		Result result;
		try {
			javaMailUtil.sendSimpleEmail("javalsj@163.com", new String[] { "315309254@qq.com", "5016904@qq.com" },
					new String[] { "wangjihong@jiuqi.com.cn" }, "您收到一封高大上的邮件，请查收。", "测试邮件逗你玩的。");
			result = ResultFactory.buildSuccessResult(null);
		} catch (Exception e) {
			result = ResultFactory.buildFailResult(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/sendTemplateEmail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Result sendTemplateEmail() {
		Result result = null;
		try {
			String thymeleafTemplatePath = "mail/mailTemplate";
			Map<String, Object> thymeleafTemplateVariable = new HashMap<String, Object>(2);
			thymeleafTemplateVariable.put("username", "javalsj");
			thymeleafTemplateVariable.put("password", "123456");
			javaMailUtil.sendTemplateEmail("javalsj@163.com", new String[] { "315309254@qq.com", "5016904@qq.com" },
					new String[] { "wangjihong@jiuqi.com.cn" }, "您收到一封高大上的邮件，请查收。", thymeleafTemplatePath,
					thymeleafTemplateVariable);
			result = ResultFactory.buildSuccessResult(null);
		} catch (Exception e) {
			result = ResultFactory.buildFailResult(e.getMessage());
		}
		return result;
	}
}
