package com.xz.rest.jersey.client.base;

import com.xz.rest.jersey.client.utils.CoderUtils;
import com.xz.rest.jetty.noXML.config.JettyConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Administrator on 2017-9-14.
 */
public abstract class Base {

    private String url;
    private String secret;

    public Base() {
        secret = CoderUtils.getBase64("ADMIN:KYLIN");
    }

    public abstract String setUrl();

    protected void buildRequest(Invocation.Builder builder) {
        builder.header("Authorization", "Basic " + this.secret);
    }

    protected abstract Response buildHTTP(Invocation.Builder builder);

    protected abstract void deal(Response response);


    public void execute() {
        Client client = null;
        Response response = null;
        JettyConfig jettyConfig = new JettyConfig() ;
        try {
            this.url = setUrl();
            client = ClientBuilder.newClient();
            String path = "http://"+ jettyConfig.getHost()+":"+jettyConfig.getPort()+"/frontEnd/rest"+this.url;
            System.out.println(path);
            URI uri = new URI(path);
            WebTarget target = client.target(uri);
            Invocation.Builder builder = target.request();
            this.buildRequest(builder);
            response = this.buildHTTP(builder);
            this.deal(response);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                response.close();
            }
            if (client!=null){
                client.close();
            }
        }

    }

}
