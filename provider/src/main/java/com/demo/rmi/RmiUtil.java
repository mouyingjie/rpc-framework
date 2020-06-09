package com.demo.rmi;

import com.demo.loadbalance.NodeInfo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by chenxyz on 2020/6/2.
 */
public class RmiUtil {

    public void startRmiServer(String host,String port,String id){
        try {
            SoaRmi soaRmi=new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.valueOf(port));
            Naming.bind("rmi://"+host+":"+port+"/"+id,soaRmi);
            System.out.println("rmi start!");
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (MalformedURLException e){

        }catch (AlreadyBoundException e){

        }

    }

    public SoaRmi startRmiClient(NodeInfo nodeInfo,String id){
        String host=nodeInfo.getHost();
        String port=nodeInfo.getPort();
        try {
            return (SoaRmi) Naming.lookup("rmi://" + host + ":" + port + "/" + id);
        }catch (RemoteException e){
                e.printStackTrace();
        }catch (MalformedURLException e){

        }catch (NotBoundException e){

        }
        return null;
    }
}
