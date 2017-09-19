package com.xz.rest.jersey.client.base.put;

import com.xz.rest.jersey.client.base.Base;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class PutStudents extends Base {
    @Override
    public String setUrl() {
        return "/student/puts";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        Entity<String> entity = Entity.entity("[{\"name\":\"x1\",\"age\":\"14\"},{\"name\":\"x1\",\"age\":\"14\"}]", MediaType.APPLICATION_JSON) ;
        return builder.put(entity);
    }

    @Override
    protected void deal(Response response) {
        String s = response.readEntity(String.class);
        System.out.println(s);
    }

    public static void main(String[] args) {
        Base base = new PutStudents() ;
        base.execute();
    }
}
