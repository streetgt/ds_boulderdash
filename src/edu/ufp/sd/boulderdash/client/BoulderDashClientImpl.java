/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.sd.boulderdash.client;

import edu.ufp.sd.boulderdash.server.BoulderDashServerRI;
import edu.ufp.sd.boulderdash.server.State;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 *
 * @author tiagocardoso
 */
public class BoulderDashClientImpl implements BoulderDashClientRI {

    private Object lastState;

    protected BoulderDashServerRI bdsRI;

    private BoulderDashClientUserGUI bdcLoginUI = new BoulderDashClientUserGUI(this);
    private BoulderDashClientHallGUI bdcHallUI = null;

    private String username;
    private String password;
    private boolean loggedin;
    private int playerGamingState;

    public BoulderDashClientImpl(BoulderDashServerRI bdsRI) throws RemoteException {
        exportObjectMethod();
        this.bdsRI = bdsRI;
    }

    private void exportObjectMethod() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            System.out.println("BoulderDashClientImpl: " + e.getMessage());
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

    public Object getLastState() {
        return lastState;
    }

    public void setLastState(Object lastState) {
        this.lastState = lastState;
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("BoulderDashClientImpl - sendMessage(): " + message);
    }

    @Override
    public String getClientUsername() throws RemoteException {
        return this.username;
    }

    @Override
    public void update() throws RemoteException {
        System.out.println("BoulderDashClientImpl - update(): ");
        this.lastState = this.bdsRI.getState();
        if (lastState instanceof State.Message) {
            System.out.println("BoulderDashClientImpl - update(): State = MessageState ");
            if (bdcHallUI != null) {
                bdcHallUI.updateMesssages();
            }
        } else if (lastState instanceof State.ConnectedClients) {
             System.out.println("BoulderDashClientImpl - update(): State = ConnectedClients ");
            if (bdcHallUI != null) {
                bdcHallUI.updateClients();
            }
        }
    }

    public void triggeredLogin(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        System.out.println("triggeredLogin");
        try {
            int login = bdsRI.login(BoulderDashClientImpl.this, username, password);
            System.out.println("int login " + login);
            if (login != 0) {
                System.out.println("You are now logged in!");
                this.setLoggedin(true);
                bdcLoginUI.setVisible(false);
                bdcHallUI = new BoulderDashClientHallGUI(this);
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void triggeredRegister(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        System.out.println("triggeredRegister");
        try {
            int register = bdsRI.register(BoulderDashClientImpl.this, username, password);
            System.out.println("int register " + register);
            if (register != 0) {
                System.out.println("You are now registed, please login!");
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    public void triggeredLogout(String username, String password) {
        System.out.println("triggeredLogout");
        try {
            this.setLoggedin(false);
            bdsRI.logout(username);
        } catch (RemoteException e) {
        }
    }
}
