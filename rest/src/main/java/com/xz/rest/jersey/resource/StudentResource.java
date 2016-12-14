package com.xz.rest.jersey.resource;

import com.xz.rest.jersey.po.Student;
import com.xz.rest.jersey.config.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.List;


@Path("student")
public class StudentResource {
	@Context
	UriInfo uriInfo;

	@Path("getAll")
	@GET
	@Produces(value = MediaType.APPLICATION_XML)
	public List<Student> getAll() {
		return DataBase.getAll();
	}

	@Path("get")
	@GET
	@Produces(value = MediaType.APPLICATION_XML)
	public Student getStudent1(@QueryParam("id") long id) {
		return DataBase.get(id);
	}

	@Path("/get/{id}")
	@GET
	@Produces(value = MediaType.APPLICATION_XML)
	public Student getStudent2(@PathParam("id") long id) {
		return DataBase.get(id);
	}

	@Path("add")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void add(@FormParam(value = "name") String name,
			@FormParam(value = "age") int age,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		long id = DataBase.put(name, age);
		URI responseUri = uriInfo.getBaseUriBuilder().path("student/get/" + id)
				.build();
		try {
			response.sendRedirect(responseUri.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") long id) {
		System.out.println(DataBase.delete(id));
	}

	@PUT
	public void put(@QueryParam("name") String name, @QueryParam("age") int age) {
		System.out.println(DataBase.put(name, age));
	}
}
