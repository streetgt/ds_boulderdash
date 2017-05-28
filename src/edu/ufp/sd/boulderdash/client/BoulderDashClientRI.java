package edu.ufp.sd.boulderdash.client;

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
public interface BoulderDashClientRI extends Remote {

    /**
     * Returns the client username
     * 
     * @return
     * @throws RemoteException 
     */
    public String getClientUsername() throws RemoteException;

    /**
     * Outputs a client message in the console
     * 
     * @param message
     * @throws RemoteException 
     */
    public void sendMessage(String message) throws RemoteException;

    /**
     * Update for receiving states
     * 
     * @throws RemoteException 
     */
    public void update() throws RemoteException;
    
    /**
     * Tells the client to update the groudView passing the "map" string array
     * 
     * @param levelSprites
     * @throws RemoteException 
     */
    public void updateGroundView(String[][] levelSprites) throws RemoteException;
    
    /**
     * Update the client score/players information panel
     * 
     * @throws RemoteException 
     */
    public void updateInformationPanel() throws RemoteException;
    
    /**
     * Sends to the client the winner / looser
     * 
     * @param winner
     * @param name
     * @throws RemoteException 
     */
    public void sendWinner(boolean winner, String name) throws RemoteException;
    
    /**
     * Plays a audio / song
     * 
     * @param song
     * @param name
     * @throws RemoteException 
     */
    public void playAudio(boolean song, String name) throws RemoteException;
    
    /**
     * Stop Audio from being played
     * 
     * @throws RemoteException 
     */
    public void stopAudio() throws RemoteException;
    
}
