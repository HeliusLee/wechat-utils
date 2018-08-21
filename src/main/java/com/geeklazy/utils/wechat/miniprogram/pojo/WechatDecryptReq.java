package com.geeklazy.utils.wechat.miniprogram.pojo;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 18:25
 * @Description
 */
public class WechatDecryptReq {
	private String encryptedData;// 加密数据
	private String iv;// 偏移量
	private String sessionKey;

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
