package com.xz.rpc.rmi.server;

import com.xz.rpc.rmi.server.interfaces.RmiAnimal;
import com.xz.rpc.rmi.server.interfaces.impl.RmiDog;
import com.xz.rpc.rpc.info.interfaces.Animal;

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
            RmiAnimal rmiAnimal = new RmiDog();
            LocateRegistry.createRegistry(9999);
            Naming.rebind("rmi://127.0.0.1:9999/Zoo", rmiAnimal);
            System.out.println("Service Start!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
