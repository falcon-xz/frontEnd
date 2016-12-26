package com.xz.rpc.baseknow.future.demo1;

/**
 * falcon -- 2016/12/22.
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client() ;
        Data data = client.request("s") ;
        System.out.println("end");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(data.getResult());
    }
}
