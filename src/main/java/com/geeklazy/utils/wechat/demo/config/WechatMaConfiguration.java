package com.geeklazy.utils.wechat.demo.config;

import com.geeklazy.utils.wechat.miniapp.api.IWechatService;
import com.geeklazy.utils.wechat.miniapp.api.impl.WechatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/22 10:57
 * @Description
 */
@Configuration
@EnableConfigurationProperties(WechatMaProperties.class)
public class WechatMaConfiguration {
	@Autowired
	private WechatMaProperties wechatMaProperties;

	@Bean
	public IWechatService wechatService() {
		System.out.println(wechatMaProperties.getAppid());
		return new WechatServiceImpl();
	}
}
