package com.demo.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by chenxyz on 2020/6/2.
 */
public interface SoaRmi extends Remote {
    public String invoke(String param) throws RemoteException;

}
