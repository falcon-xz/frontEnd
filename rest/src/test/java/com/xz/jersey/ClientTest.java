package com.xz.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;


public class ClientTest {
	private String baseUrl = "http://localhost:8080/jersey/rest/student";

	@Test
	public void testSecret() {
		String s = ClientTest.getBase64("ADMIN:KYLIN") ;
		System.out.println(s);
		assert s.equals("QURNSU46S1lMSU4=") ;
	}

	@Test
	public void testGet() {
		Client client = ClientBuilder.newClient();
		WebTarget baseTarget= client.target("http://bdapp134:7070/");
		WebTarget subTarget=baseTarget.path("kylin/api/cubes");
		String secret = "ADMIN:KYLIN";
		String base64Secret = ClientTest.getBase64(secret) ;
		Response response = subTarget.request().header("Authorization","Basic "+base64Secret).get();
		String s = response.readEntity(String.class) ;
		System.out.println(s);
		response.close();
	}

	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	@Test
	public void testPost() {

	}

}
