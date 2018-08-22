package com.geeklazy.utils.wechat.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/22 10:58
 * @Description
 */
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMaProperties {
	private String appid;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
}
