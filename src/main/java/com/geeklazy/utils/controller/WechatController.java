package com.geeklazy.utils.controller;

import com.geeklazy.utils.wechat.GeekLazyWechatUtils;
import com.geeklazy.utils.wechat.miniprogram.api.resp.Jscode2SessionResp;
import com.geeklazy.utils.wechat.miniprogram.pojo.WechatDecryptReq;
import com.geeklazy.utils.wechat.miniprogram.pojo.WechatUserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 15:04
 * @Description
 */
@RestController
public class WechatController {
	@RequestMapping("/decrypt")
	public WechatUserInfo decryptWechatData(WechatDecryptReq decryptReq){
		WechatUserInfo userInfo;
		try {
			userInfo = GeekLazyWechatUtils.decrypt(decryptReq,WechatUserInfo.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return userInfo;
	}

	@RequestMapping("/login")
	public Jscode2SessionResp login(String code){
		Jscode2SessionResp jscode2SessionResp;
		try {
			jscode2SessionResp = GeekLazyWechatUtils.jscode2session(code,Jscode2SessionResp.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return jscode2SessionResp;
	}
}
