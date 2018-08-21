package com.geeklazy.utils.wechat.miniprogram.api;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 18:38
 * @Description
 */
public enum WechatMiniProgramAPI {
	JSCODE2SESSION("jscode2session", "https://api.weixin.qq.com/sns/jscode2session"),
	;
	private String name;
	private String url;

	WechatMiniProgramAPI(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
