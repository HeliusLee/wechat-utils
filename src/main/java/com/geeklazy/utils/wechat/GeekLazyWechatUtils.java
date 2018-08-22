package com.geeklazy.utils.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeklazy.utils.wechat.commons.HttpResp;
import com.geeklazy.utils.wechat.commons.HttpUtils;
import com.geeklazy.utils.wechat.miniapp.api.WechatMiniProgramAPI;
import com.geeklazy.utils.wechat.miniapp.pojo.WechatDecryptReq;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 15:26
 * @Description
 */
public class GeekLazyWechatUtils {
	public static <T> T jscode2session(String code, Class<T> clazz){
		String content = jscode2session(code);
		try {
			return new ObjectMapper().readValue(content, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String jscode2session(String code){
		HttpResp httpResp;
		try {
			httpResp = HttpUtils.sendGet(WechatMiniProgramAPI.JSCODE_TO_SESSION.getUrl());
			return httpResp.getContent();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static <T> T decrypt(WechatDecryptReq decryptReq, Class<T> clazz) {
		String decryptJsonStr = decrypt(decryptReq);
		try {
			return new ObjectMapper().readValue(decryptJsonStr, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(WechatDecryptReq decryptReq){
		byte[] dataByte = Base64.getDecoder().decode(decryptReq.getEncryptedData());// 被加密的数据
		byte[] keyByte = Base64.getDecoder().decode(decryptReq.getSessionKey());// 加密秘钥
		byte[] ivByte = Base64.getDecoder().decode(decryptReq.getIv());// 偏移量

		int base = 16;
		// 判断秘钥密钥是否16位
		if (keyByte.length % base != 0) {// 不足,补全
			int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
			keyByte = temp;
		}

		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);

			// 解密结果
			byte[] resultByte = cipher.doFinal(dataByte);

			// 判断解密是否成功
			if (resultByte != null && resultByte.length > 0) {// 成功
				return new String(resultByte, "UTF-8");
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException | InvalidParameterSpecException e) {
			e.printStackTrace();
			return null;
		}

		// 失败
		return null;
	}
}
