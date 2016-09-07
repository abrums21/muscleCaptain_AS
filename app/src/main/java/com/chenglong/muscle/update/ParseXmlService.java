package com.chenglong.muscle.update;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.media.MediaCodecInfo.CodecCapabilities;

public class ParseXmlService {

	public HashMap<String, Object> parseXml(String file) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		FileInputStream is = new FileInputStream(new File(file));
		Document document = builder.parse(is);
		
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				if ("version".equals(element.getNodeName()))
				{
					String version = element.getFirstChild().getNodeValue();
					map.put("version", version);
				}
				else if ("name".equals(element.getNodeName()))
				{
					String name = element.getFirstChild().getNodeValue();
					map.put("name", name);
				}
				else if ("info".equals(element.getNodeName()))
				{
					String info = element.getFirstChild().getNodeValue();
					map.put("info", info);
				}
				else if ("url".equals(element.getNodeName()))
				{
					String url = element.getFirstChild().getNodeValue();
					map.put("url", url);
				}
				else if ("patch".equals(element.getNodeName()))
				{
					String apk = element.getAttribute("apk");
					NodeList nodeList = element.getChildNodes();
					String url = "";
					String md5 = "";
					String name = "";
					for (int j = 0; j < nodeList.getLength(); j++)
					{
						Node patchNode = nodeList.item(j);
						if (patchNode.getNodeType() == Node.ELEMENT_NODE)
						{
							Element ele = (Element)patchNode;
							if ("name".equals(ele.getNodeName()))
							{
								name = ele.getFirstChild().getNodeValue();
							}
							else if ("url".equals(ele.getNodeName()))
							{
								url = ele.getFirstChild().getNodeValue();
							}
							else if ("md5".equals(ele.getNodeName()))
							{
								md5 = ele.getFirstChild().getNodeValue();
							}
						}
					}
					map.put(apk, new PatchCfg(name, url, md5));
				}
			}
		}
		return map;
	}
}
