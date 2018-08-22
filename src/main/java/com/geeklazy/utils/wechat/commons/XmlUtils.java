package com.geeklazy.utils.wechat.commons;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author heliuslee@live.cn
 * @Date 2018/08/22 10:13
 * @Description
 */
public class XmlUtils {
	// 复杂map和xml转换使用递归思想，微信中都是简单xml故不做展开，dom操作可使用dom4j,jdom，sax等
	/* 简单xml转map */
	public static Map<String, String> xml2Map(String xmlStr) {
		return xml2Map(xmlStr, "UTF-8");
	}

	/* 简单xml转map */
	public static Map<String, String> xml2Map(String xmlStr, String charsetName) {
		try {
			xmlStr.replaceAll("[\r\n]", "");// 消除换行符
			Map<String, String> map = new HashMap<>();// 存放转换结果
			InputStream in = new ByteArrayInputStream(xmlStr.getBytes(charsetName));// 获取输入流

			Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);// 将xml输入流转换为DOM
			dom.getDocumentElement().normalize();// 除空的文本节点，并连接相邻的文本节点

			NodeList nodeList = dom.getDocumentElement().getChildNodes();// 获取根节点下所有子节点

			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				// 判断是元素节点
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					map.put(element.getNodeName(), element.getTextContent());
				}
			}
			try {
				in.close();
			} catch (Exception e) {
				// do nothing
			}
			return map;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* 简单map转xml */
	public static String map2Xml(Map<String, String> map) {
		return map2Xml(map, "UTF-8");
	}

	/* 简单map转xml */
	public static String map2Xml(Map<String, String> map, String charsetName) {
		try {
			Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();// 创建dom存储结果
			Element root = dom.createElement("xml");// 创建根节点
			dom.appendChild(root);


			for (String key : map.keySet()) {
				String value = map.get(key);// 根据主键获取对应值
				if (value == null) {// null值处理
					value = "";
				}
				value = value.trim();
				Element child = dom.createElement(key);// 创建子元素节点
				child.appendChild(dom.createTextNode(value));// 子元素添加文本节点
				root.appendChild(child);// 将子元素节点添加到父元素节点
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, charsetName);// 设置编码格式
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置缩进

			// 打开输出流
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			DOMSource source = new DOMSource(dom);
			transformer.transform(source, result);

			String out = writer.getBuffer().toString();//.replaceAll("[\n\r]", "");

			writer.close();
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
