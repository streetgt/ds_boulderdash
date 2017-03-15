package edu.ufp.sd.boulderdash.server;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public class BoulderDashServerImpl extends UnicastRemoteObject implements BoulderDashServerRI {

    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public BoulderDashServerImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
    }

    public void print(String msg) throws RemoteException {
        System.out.println("BoulderDashServerImpl: print(\"" + msg +"\")");
    }
}
