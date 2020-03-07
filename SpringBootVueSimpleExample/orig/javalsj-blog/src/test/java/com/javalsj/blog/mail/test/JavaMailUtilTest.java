package com.javalsj.blog.mail.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javalsj.blog.autoconfigure.mail.JavaMailService;

/**
 * @description 发送邮件测试
 * @author WANGJIHONG
 * @date 2018年3月14日 下午5:28:05
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaMailUtilTest {

	@Autowired
	private JavaMailService javaMailService;			

	@Test
	public void sendTemplateEmail() throws Exception {
		String thymeleafTemplatePath = "mail/mailTemplate";
		Map<String, Object> thymeleafTemplateVariable = new HashMap<String, Object>(2);
		thymeleafTemplateVariable.put("username", "javalsj");
		thymeleafTemplateVariable.put("password", "123456");
		boolean isSuccess = true;
		try {
			javaMailService.sendTemplateEmail("javalsj@163.com", 
					new String[] { "315309254@qq.com", "5016904@qq.com" },
					new String[] { "wangjihong@jiuqi.com.cn" }, 
					"您收到一封高大上的邮件，请查收。",
					thymeleafTemplatePath,
					thymeleafTemplateVariable);
		} catch (Exception e) {
			isSuccess = false;
		}
		Assert.assertTrue(isSuccess);
	}
}
