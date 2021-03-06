package com.xz.rest.jersey.client.base.get;

import com.xz.rest.jersey.client.base.Base;
import com.xz.rest.jersey.po.Student;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class GetStudent1 extends Base {
    @Override
    public String setUrl() {
        return "/student/get/2";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        return builder.get();
    }

    @Override
    protected void deal(Response response) {
        Student student = response.readEntity(Student.class);
        System.out.println(student);
    }

    public static void main(String[] args) {
        Base base = new GetStudent1() ;
        base.execute();
    }
}

