package com.xz.rest.jersey.client.base.put;

import com.xz.rest.jersey.client.base.Base;
import com.xz.rest.jersey.po.Student;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class PutAndShowStudent extends Base {
    @Override
    public String setUrl() {
        return "/student/addAndShow";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        Entity<String> entity = Entity.entity("name=x1z&age=71", MediaType.APPLICATION_FORM_URLENCODED_TYPE) ;
        return builder.put(entity);
    }

    @Override
    protected void deal(Response response) {
        Student s = response.readEntity(Student.class);
        System.out.println(s);
    }

    public static void main(String[] args) {
        Base base = new PutAndShowStudent() ;
        base.execute();
    }
}
