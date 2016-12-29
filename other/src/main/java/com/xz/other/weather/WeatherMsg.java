package com.xz.other.weather;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

/**
 * 解析xml文档，包括本地文档和url
 * 
 */
public class WeatherMsg {
	private Map<Integer, Weather> map = new HashMap<>();

	private String cityName;

	public WeatherMsg(String cityName) {
		this.cityName = cityName;
		getDayWeather(0);
		getDayWeather(1);
		getDayWeather(2);
	}

	private void getDayWeather(int day) {
		try {
			Map<String, String> contents = new HashMap<>();
			contents.put("day", day + "");
			String link = "http://php.weather.sina.com.cn/xml.php?city="
					+ URLEncoder.encode(cityName, "GBK")
					+ "&password=DJOYnieT8234jlsK&day=" + day;
			System.out.println(link);
			URL url = new URL(link);
			SAXReader reader = new SAXReader();
			Document document = reader.read(url);

			List<Node> list = document.selectNodes("//Profiles/Weather/*");
			for (int i = 0; i < list.size(); i++) {
				Node node = list.get(i);
				contents.put(node.getName(), node.getText());
			}
			Weather weather = new Weather(contents);
			map.put(day, weather);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public String getWeatherInfo() {
		Weather day1 = map.get(0) ;
		Weather day2 = map.get(1) ;
		Weather day3 = map.get(2) ;
		return day1.toString()+day2.toString()+day3.toString() ;
	}

	public static void main(String[] args) {
		String city = "南京";
		WeatherMsg weather = new WeatherMsg(city);
		System.out.println(weather.getWeatherInfo());
	}
}