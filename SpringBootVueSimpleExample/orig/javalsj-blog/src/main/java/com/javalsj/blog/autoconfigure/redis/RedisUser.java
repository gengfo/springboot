package com.javalsj.blog.autoconfigure.redis;

import java.io.Serializable;

/**
 * @description Redis用户实体
 * @author WANGJIHONG
 * @date 2018年3月26日 下午7:45:48 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class RedisUser implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;

    public RedisUser() {
	}

	public RedisUser(String username) {
        this.username = username;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
