package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public interface BoulderDashServerRI extends Remote {
    public void print(String msg) throws RemoteException;
    public int login(BoulderDashClientRI client, String username, String password) throws RemoteException;
    public int logout(String username) throws RemoteException;
}
