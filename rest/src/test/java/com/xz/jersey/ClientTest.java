package com.xz.jersey;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ClientTest {
	private String baseUrl = "http://localhost:8080/jersey/rest/student";

	@Test
	public void testDelete() {
		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl + "/3");
		ClientResponse response = webResource.delete(ClientResponse.class);

		System.out.println("Response for delete request: "
				+ response.getStatus());
	}

	@Test
	public void testPut() {
		Client client = Client.create();
		WebResource webResource = client.resource(baseUrl);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("name", "222");
		queryParams.add("age", "2222");
		ClientResponse response = webResource.queryParams(queryParams).put(
				ClientResponse.class);
		System.out.println("Response for put request: " + response.getStatus());

	}

}
