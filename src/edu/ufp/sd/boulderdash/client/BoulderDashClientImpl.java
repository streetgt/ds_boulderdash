/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.sd.boulderdash.client;

import edu.ufp.sd.boulderdash.server.BoulderDashServerRI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 *
 * @author tiagocardoso
 */
public class BoulderDashClientImpl implements BoulderDashClientRI {
    
    private BoulderDashClientRI bdcRI;
    private BoulderDashServerRI bdsRI;
        
    private BoulderDashClientLoginGUI bdcLoginUI;
     
    private String username;
    private String password;

    public BoulderDashClientImpl(BoulderDashServerRI bdsRI) {
        exportObjectMethod();
        this.bdsRI = bdsRI;
        bdcLoginUI = new BoulderDashClientLoginGUI(this);
    }
    
    private void exportObjectMethod()
    {     
        try {
            UnicastRemoteObject.exportObject(this,0);
        } catch (RemoteException e) {
            System.out.println("BoulderDashClientImpl: " + e.getMessage());
        }
        
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("BoulderDashClientImpl - sendMessage(): " + message);
    }

    @Override
    public String getUsername() throws RemoteException {
        return this.username;
    }
    
    public void triggeredLogin(String username, String password){
        this.username = username;
        System.out.println("triggeredLogin");
        try {
            int login = bdsRI.login(BoulderDashClientImpl.this, username,password);
            System.out.println("int login " + login);
            if(login == 1) {
                System.out.println("You are now logged in!");
            }
             
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void triggeredLogout(String username, String password)
    {
        System.out.println("triggeredLogout");
        try {
            bdsRI.logout(username);   
        } catch (Exception e) {
        }
    }
   
}
