package com.geeklazy.utils.wechat.miniprogram.api.resp;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 19:05
 * @Description
 */
public class Jscode2SessionResp {
	private String session_key;
	private String openid;
	private String unionid;
	private String errcode;

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
}
