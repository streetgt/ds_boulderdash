package edu.ufp.sd.boulderdash.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>
 * Title: Projecto SD - BoulderDash - BoulderDash</p>
 * <p>
 * Description: Jogo BoulderDash destribuido</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Tiago Cardoso <tiagocardosoweb@gmail.com>
 * @author Miguel Ferreira <migueelfsf@gmail.com>
 * @version 1.0
 */
public interface BoulderDashClientRI extends Remote {

    public String getClientUsername() throws RemoteException;

    public void sendMessage(String message) throws RemoteException;

    public void update() throws RemoteException;
    
    public void updateGroundView(String[][] levelSprites) throws RemoteException;
    
    public void updateInformationPanel() throws RemoteException;
    
    public void sendWinner(boolean winner, String name) throws RemoteException;
    
    public void playAudio(boolean song, String name) throws RemoteException;
    
    public void stopAudio() throws RemoteException;
    
}
