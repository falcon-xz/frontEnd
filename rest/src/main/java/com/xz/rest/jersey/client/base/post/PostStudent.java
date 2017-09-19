package com.xz.rest.jersey.client.base.post;

import com.xz.rest.jersey.client.base.Base;
import com.xz.rest.jersey.client.base.get.GetStudents;
import com.xz.rest.jersey.po.Student;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class PostStudent extends Base {
    @Override
    public String setUrl() {
        return "/student/post";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        Entity<String> entity = Entity.entity("id=9&name=xxxx&age=1121", MediaType.APPLICATION_FORM_URLENCODED_TYPE) ;
        return builder.post(entity);
    }

    @Override
    protected void deal(Response response) {
        String bo = response.readEntity(String.class);
        System.out.println(bo);
    }

    public static void main(String[] args) {
        Base base = new PostStudent() ;
        base.execute();
    }
}

