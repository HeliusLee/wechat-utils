package com.geeklazy.utils.wechat.commons;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/21 18:51
 * @Description
 */
public class HttpUtils {
	private static String defaultContentEncoding = Charset.defaultCharset().name();

	private HttpUtils() {
	}

	/**
	 * 发送GET请求
	 */
	public static HttpResp sendGet(String urlString) throws IOException {
		return send(urlString, "GET", (String) null, null);
	}

	/**
	 * 发送GET请求
	 */
	public static HttpResp sendGet(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "GET", params, null);
	}

	/**
	 * 发送GET请求
	 */
	public static HttpResp sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "GET", params, propertys);
	}

	/**
	 * 发送POST请求
	 */
	public static HttpResp sendPost(String urlString) throws IOException {
		return send(urlString, "POST", (String) null, null);
	}

	/**
	 * 发送POST请求
	 */
	public static HttpResp sendPost(String urlString, Map<String, String> params) throws IOException {
		return send(urlString, "POST", params, null);
	}

	/**
	 * 发送POST请求
	 */
	public static HttpResp sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
		return send(urlString, "POST", params, propertys);
	}

	/**
	 * 发送HTTP请求
	 */
	private static HttpResp send(String urlStr, String method, Map<String, String> parameters, Map<String, String> reqProperties) throws IOException {
		String data = null;

		if (parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			data = param.toString();
		}
		return send(urlStr, method, data, reqProperties);
	}

	/**
	 * 发送HTTP请求
	 */
	public static HttpResp send(String urlStr, String method, String data, Map<String, String> reqProperties) throws IOException {

		// GET请求——数据直接拼接url后
		if ("GET".equalsIgnoreCase(method) && data != null) {
			urlStr += "?" + data;
			data = null;
		}

		URL url = new URL(urlStr);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();// 建立http连接

		// 设置连接属性
		urlConnection.setRequestMethod(method);// 设置请求方法
		urlConnection.setDoOutput("POST".equals(method));// 设置允许输出
		urlConnection.setDoInput(true);// 设置允许输入
		urlConnection.setUseCaches(false);// 设置不用缓存

		if (reqProperties != null) for (String key : reqProperties.keySet()) {
			urlConnection.addRequestProperty(key, reqProperties.get(key));
		}

		// 开始连接请求
		urlConnection.connect();

		if (data != null) {
			byte[] dataBytes = data.getBytes("UTF-8");// 文件是什么代码格式就用该格式解码
			// urlConnection.setRequestProperty("Content-Length", String.valueOf(dataBytes.length));// 设置文件长度

			// 输出流
			OutputStream out = urlConnection.getOutputStream();
			out.write(dataBytes);
			out.flush();
			out.close();

			System.out.println(urlConnection.getResponseCode());
		}

		return makeContent(urlStr, urlConnection);
	}

	/**
	 * 得到响应对象
	 */
	private static HttpResp makeContent(String urlStr, HttpURLConnection urlConn) throws IOException {
		// 输入流
		HttpResp resp = new HttpResp();
		try {
			InputStream in = urlConn.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			resp.setContentCollection(new Vector<String>());
			StringBuilder temp = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				resp.getContentCollection().add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();

			String ecod = urlConn.getContentEncoding();
			if (ecod == null) ecod = defaultContentEncoding;

			resp.setUrlString(urlStr);

			resp.setDefaultPort(urlConn.getURL().getDefaultPort());
			resp.setFile(urlConn.getURL().getFile());
			resp.setHost(urlConn.getURL().getHost());
			resp.setPath(urlConn.getURL().getPath());
			resp.setPort(urlConn.getURL().getPort());
			resp.setProtocol(urlConn.getURL().getProtocol());
			resp.setQuery(urlConn.getURL().getQuery());
			resp.setRef(urlConn.getURL().getRef());
			resp.setUserInfo(urlConn.getURL().getUserInfo());

			resp.setContent(new String(temp.toString().getBytes(), ecod));
			resp.setContentEncoding(ecod);
			resp.setCode(urlConn.getResponseCode());
			resp.setMessage(urlConn.getResponseMessage());
			resp.setContentType(urlConn.getContentType());
			resp.setMethod(urlConn.getRequestMethod());
			resp.setConnectTimeout(urlConn.getConnectTimeout());
			resp.setReadTimeout(urlConn.getReadTimeout());

			return resp;
		} finally {
			if (urlConn != null) urlConn.disconnect();// 关闭连接
		}
	}

	/**
	 * 默认的响应字符集
	 */
	public String getDefaultContentEncoding() {
		return defaultContentEncoding;
	}

	/**
	 * 设置默认的响应字符集
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		HttpUtils.defaultContentEncoding = defaultContentEncoding;
	}
}
