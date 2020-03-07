package com.javalsj.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description 主页控制器
 * @author WANGJIHONG
 * @date 2018年3月15日 下午10:35:31
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Controller
public class IndexController {

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/index";
	}
	
	@GetMapping(value = "/index")
	public String index(Model model) {
		return "login/login";
	}

}