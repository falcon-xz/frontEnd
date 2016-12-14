package com.xz.rpc.rmi.client;

import com.xz.rpc.rmi.server.interfaces.Animal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * falcon -- 2016/12/12.
 */
public class ClientMain {
    public static void main(String[] args) {
        try {
            Animal animal= (Animal)Naming.lookup("rmi://127.0.0.1:9999/Animal");
            System.out.println(animal.getClass());
            System.out.println(animal.jiao());
            System.out.println(animal.feed("ff"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
