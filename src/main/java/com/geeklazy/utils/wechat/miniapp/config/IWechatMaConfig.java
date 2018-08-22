package com.geeklazy.utils.wechat.miniapp.config;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/22 14:19
 * @Description
 */
public interface IWechatMaConfig {
	String getAppId();// 获取appId
	String getSecret();// 获取secret
	String getToken();// 获取token
	String getAesKey();
	String getMsgDataFormat();
	long getExpiresTime();
	String getHttpProxyHost();
	int getHttpProxyPort();
	String getHttpProxyUsername();
	String getHttpProxyPassword();
}