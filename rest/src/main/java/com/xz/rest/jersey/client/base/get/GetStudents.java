package com.xz.rest.jersey.client.base.get;

import com.xz.rest.jersey.client.base.Base;
import com.xz.rest.jersey.po.Student;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class GetStudents extends Base {
    @Override
    public String setUrl() {
        return "/student/getAll";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        return builder.get();
    }

    @Override
    protected void deal(Response response) {
        Student[] students = response.readEntity(Student[].class);
        for (Student s:students) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Base base = new GetStudents() ;
        base.execute();
    }
}
