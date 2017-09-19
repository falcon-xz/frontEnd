package com.xz.rest.jersey.client.base.delete;

import com.xz.rest.jersey.client.base.Base;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2017-9-19.
 */
public class DeleteStudent extends Base {
    @Override
    public String setUrl() {
        return "/student/delete/4";
    }

    @Override
    protected Response buildHTTP(Invocation.Builder builder) {
        return builder.delete();
    }

    @Override
    protected void deal(Response response) {
        boolean bo = response.readEntity(boolean.class);
        System.out.println(bo);
    }

    public static void main(String[] args) {
        Base base = new DeleteStudent() ;
        base.execute();
    }
}
