package com.xz.rpc.rmi.server;

import com.xz.rpc.rmi.server.interfaces.Animal;
import com.xz.rpc.rmi.server.interfaces.impl.Dog;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * falcon -- 2016/12/12.
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            Animal animal = new Dog();
            LocateRegistry.createRegistry(9999);
            Naming.rebind("rmi://127.0.0.1:9999/Animal", animal);
            System.out.println("Service Start!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
