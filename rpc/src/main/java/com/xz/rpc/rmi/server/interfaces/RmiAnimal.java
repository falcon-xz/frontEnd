package com.xz.rpc.rmi.server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * falcon -- 2016/12/12.
 */
public interface RmiAnimal extends Remote{
    String jiao() throws RemoteException;
    String feed(String s) throws RemoteException;
}
