package com.xz.rpc.rmi.client;

import com.xz.rpc.rmi.server.interfaces.RmiAnimal;
import com.xz.rpc.rpc.info.interfaces.Animal;

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
            RmiAnimal rmiAnimal= (RmiAnimal)Naming.lookup("rmi://127.0.0.1:9999/Zoo");
            System.out.println(rmiAnimal.getClass());
            System.out.println(rmiAnimal.jiao());
            System.out.println(rmiAnimal.feed("ff"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
