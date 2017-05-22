/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.sd.boulderdash.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author tiagocardoso
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
