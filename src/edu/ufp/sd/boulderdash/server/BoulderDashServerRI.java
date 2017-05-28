package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>
 * Title: Distributed Systems Project - BoulderDash</p>
 * <p>
 * Description: BoulderDash Game Multiplayer - Distributed using RMI</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Tiago Cardoso <tiagocardosoweb@gmail.com>
 * @author Miguel Ferreira <migueelfsf@gmail.com>
 * @version 0.1
 */
public interface BoulderDashServerRI extends Remote {

    //Start: Observer Design Pattern
    public void attach(BoulderDashClientRI client) throws RemoteException;

    public void detach(BoulderDashClientRI client) throws RemoteException;

    public Object getState() throws RemoteException;

    public void setState(Object s) throws RemoteException;
    //End: Observer Design Pattern
    
    /**
     * Prints in the console
     * 
     * @param msg
     * @throws RemoteException 
     */
    public void print(String msg) throws RemoteException;

    /**
     * Method to verify login
     * 
     * @param client
     * @param username
     * @param password
     * @return
     * @throws RemoteException 
     */
    public int login(BoulderDashClientRI client, String username, String password) throws RemoteException;

    /**
     * Method that is used for logout
     * 
     * @param username
     * @return
     * @throws RemoteException 
     */
    public int logout(String username) throws RemoteException;

    /**
     * Method used for register
     * 
     * @param client
     * @param username
     * @param password
     * @return
     * @throws RemoteException 
     */
    public int register(BoulderDashClientRI client, String username, String password) throws RemoteException;

    /**
     * Returns the current online clients
     * @return
     * @throws RemoteException 
     */
    public int countConnectedClients() throws RemoteException;

    /**
     * Creates a room for play with other player
     * 
     * @param client
     * @param level
     * @return
     * @throws RemoteException 
     */
    public int createGameRoom(BoulderDashClientRI client, String level) throws RemoteException;

    /**
     * Fetch the Levels avaliable
     * 
     * @return
     * @throws RemoteException 
     */
    public String[] fetchAvaliableLevels() throws RemoteException;

    /**
     * Fetch the Rooms avaliable
     * @return
     * @throws RemoteException 
     */
    public String[] fetchAvaliableRooms() throws RemoteException;

    // SERVER:
    /**
     * Inserts a client in a room
     * 
     * @param client
     * @param roomID
     * @return
     * @throws RemoteException 
     */
    public boolean addClientToRoom(BoulderDashClientRI client, int roomID) throws RemoteException;

    /**
     * Removes the client from a room
     * 
     * @param client
     * @param roomID
     * @throws RemoteException 
     */
    public void removeClientFromRoom(BoulderDashClientRI client, int roomID) throws RemoteException;

    /**
     * Method used for client send keys to the room
     * 
     * @param client
     * @param roomID
     * @param direction
     * @throws RemoteException 
     */
    public void sendKeys(BoulderDashClientRI client, int roomID, String direction) throws RemoteException;

    /**
     * Given a index returns the client username
     * 
     * @param roomID
     * @param index
     * @return
     * @throws RemoteException 
     */
    public String getClientNameInRoom(int roomID, int index) throws RemoteException;

    /**
     * Given a index returns the client current score
     * 
     * @param roomID
     * @param index
     * @return
     * @throws RemoteException 
     */
    public int getClientScoreInRoom(int roomID, int index) throws RemoteException;

    /**
     * Gets the remaining diamonds from a room
     * 
     * @param roomID
     * @return
     * @throws RemoteException 
     */
    public int getRoomRemainingDiamonds(int roomID) throws RemoteException;

    /**
     * Returns the room "ground" map size
     * 
     * @param roomID
     * @return
     * @throws RemoteException 
     */
    public int[] getRoomMapSize(int roomID) throws RemoteException;
}
