package com.xz.rest.jersey.resource;

import com.xz.rest.jersey.po.Student;
import com.xz.rest.jersey.config.DataBase;

import javax.servlet.ServletException;
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

	@Path("/getAll")
	@GET
	@Produces(value = MediaType.APPLICATION_XML)
	public List<Student> getAll() {
		return DataBase.getAll();
	}

	@Path("/get")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public Student getStudent1(@QueryParam("id") Integer id) throws ServletException{
		if (id==null){
			throw new ServletException("参数id为null");
		}
		return DataBase.get(id);
	}

	@Path("/get/{id}")
	@GET
	@Produces(value = MediaType.APPLICATION_XML)
	public Student getStudent2(@PathParam("id") int id) {
		return DataBase.get(id);
	}

	@Path("/put")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(value = MediaType.APPLICATION_JSON)
	public int put(@FormParam(value = "name") String name,
			@FormParam(value = "age") Integer age
			) throws ServletException {
		if (name == null || name.equals("")){
			throw new ServletException("name 没有");
		}
		int id = DataBase.put(name, age);
		return id ;
	}

	@Path("/puts")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String puts(Student[] students) {
		StringBuffer sb = new StringBuffer() ;
		for (int i = 0; i < students.length; i++) {
			int id = DataBase.put(students[i].getName(), students[i].getAge());
			sb.append(id).append("--") ;
		}
		return sb.toString() ;
	}

	@Path("/addAndShow")
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Student addAndShow(@FormParam(value = "name") String name,
					@FormParam(value = "age") int age,
					@Context HttpServletRequest request,
					@Context HttpServletResponse response) {
		int id = DataBase.put(name, age);
		return this.getStudent2(id) ;
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public String delete(@PathParam("id") int id) {
		boolean bo = DataBase.delete(id);
		return bo+"" ;
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(value = MediaType.APPLICATION_XML)
	public String post(@FormParam("id") int id,@FormParam("name") String name, @FormParam("age") int age) {
		return DataBase.update(id,name,age) +"";
	}
}
