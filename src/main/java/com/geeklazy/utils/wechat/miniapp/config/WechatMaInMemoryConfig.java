package com.geeklazy.utils.wechat.miniapp.config;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/22 14:21
 * @Description
 */
public class WechatMaInMemoryConfig implements IWechatMaConfig {

	protected volatile String appId;
	protected volatile String secret;
	protected volatile String token;
	protected volatile String accessToken;
	protected volatile String aesKey;
	protected volatile String msgDataFormat;
	protected volatile long expiresTime;

	protected volatile String httpProxyHost;
	protected volatile int httpProxyPort;
	protected volatile String httpProxyUsername;
	protected volatile String httpProxyPassword;

	protected Lock accessTokenLock = new ReentrantLock();

	@Override
	public String getAppId() {
		return this.appId;
	}

	@Override
	public String getSecret() {
		return this.secret;
	}

	@Override
	public String getToken() {
		return this.token;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	@Override
	public String getAesKey() {
		return this.aesKey;
	}

	@Override
	public String getMsgDataFormat() {
		return this.msgDataFormat;
	}

	@Override
	public long getExpiresTime() {
		return this.expiresTime;
	}

	@Override
	public String getHttpProxyHost() {
		return this.httpProxyHost;
	}

	@Override
	public int getHttpProxyPort() {
		return this.httpProxyPort;
	}

	@Override
	public String getHttpProxyUsername() {
		return this.httpProxyUsername;
	}

	@Override
	public String getHttpProxyPassword() {
		return this.httpProxyPassword;
	}
}
