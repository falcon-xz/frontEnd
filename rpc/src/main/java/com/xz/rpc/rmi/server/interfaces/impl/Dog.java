package com.xz.rpc.rmi.server.interfaces.impl;

import com.xz.rpc.rmi.server.interfaces.Animal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * falcon -- 2016/12/12.
 */
public class Dog extends UnicastRemoteObject implements Animal {

    public Dog() throws RemoteException {
        super();
    }

    @Override
    public String jiao() throws RemoteException{
        return getClass().getName()+"叫";
    }

    @Override
    public String feed(String s) throws RemoteException{
        return getClass().getName()+"吃"+s;
    }
}
