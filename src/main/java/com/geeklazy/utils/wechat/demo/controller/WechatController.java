package com.geeklazy.utils.wechat.demo.controller;

import com.geeklazy.utils.wechat.GeekLazyWechatUtils;

import com.geeklazy.utils.wechat.miniapp.api.IWechatService;
import com.geeklazy.utils.wechat.miniapp.pojo.WechatDecryptReq;
import com.geeklazy.utils.wechat.miniapp.pojo.WechatUserInfo;
import com.geeklazy.utils.wechat.miniapp.pojo.resp.Jscode2SessionResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 15:04
 * @Description
 */
@RestController
public class WechatController {
	@Autowired
	private IWechatService wechatService;
	@RequestMapping("/decrypt")
	public WechatUserInfo decryptWechatData(WechatDecryptReq decryptReq){
		System.out.println(wechatService);
		WechatUserInfo userInfo;
		try {
			userInfo = GeekLazyWechatUtils.decrypt(decryptReq,WechatUserInfo.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return userInfo;
	}

	@RequestMapping("/login")
	public Jscode2SessionResp loginMiniProgram(String code){
		Jscode2SessionResp jscode2SessionResp;
		try {
			jscode2SessionResp = GeekLazyWechatUtils.jscode2session(code,Jscode2SessionResp.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return jscode2SessionResp;
	}
}
