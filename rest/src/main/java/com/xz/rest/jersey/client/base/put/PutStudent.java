package com.xz.rest.jersey.client.base.put;

import com.xz.rest.jersey.client.base.Base;
import com.xz.rest.jersey.client.base.delete.DeleteStudent;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class PutStudent extends Base {
    @Override
    public String setUrl() {
        return "/student/put";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        Entity<String> entity = Entity.entity("name=xz&age=21", MediaType.APPLICATION_FORM_URLENCODED_TYPE) ;
        return builder.put(entity);
    }

    @Override
    protected void deal(Response response) {
        int i = response.readEntity(int.class);
        System.out.println(i);
    }

    public static void main(String[] args) {
        Base base = new PutStudent() ;
        base.execute();
    }
}

