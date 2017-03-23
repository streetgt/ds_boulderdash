package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>
 * Title: Projecto SD - BoulderDash</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2016</p>
 * <p>
 * Company: UFP </p>
 *
 */
public interface BoulderDashServerRI extends Remote {

    //Start: Observer Design Pattern
    public void attach(BoulderDashClientRI client) throws RemoteException;

    public void detach(BoulderDashClientRI client) throws RemoteException;

    public Object getState() throws RemoteException;

    public void setState(Object s) throws RemoteException;

    //End: 
    public void print(String msg) throws RemoteException;

    public int login(BoulderDashClientRI client, String username, String password) throws RemoteException;

    public int logout(String username) throws RemoteException;

    public int register(BoulderDashClientRI client, String username, String password) throws RemoteException;

    public int countConnectedClients() throws RemoteException;

    public boolean createGameRoom(BoulderDashClientRI client, String level) throws RemoteException;

    public String[] fetchAvaliableLevels() throws RemoteException;

    public String[] fetchAvaliableRooms() throws RemoteException;

    public void sendKeys(BoulderDashClientRI client, String string) throws RemoteException;
}
