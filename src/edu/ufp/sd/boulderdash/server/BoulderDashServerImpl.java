package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2009</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 1.0
 */
public class BoulderDashServerImpl extends UnicastRemoteObject implements BoulderDashServerRI {

    private Object state;

    private BoulderDashServerGUI bdsGUI;

    private ArrayList<BoulderDashClientRI> clients = new ArrayList<>();

    public static String PATH_USERS = "../../data/users/";

    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public BoulderDashServerImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
        this.bdsGUI = new BoulderDashServerGUI(this);
    }

    @Override
    public void print(String msg) throws RemoteException {
        System.out.println("BoulderDashServerImpl: print(\"" + msg + "\")");
    }

    @Override
    public synchronized int login(BoulderDashClientRI client, String username, String password) throws RemoteException {
        System.out.println("BoulderDashServerImpl - login(): " + username + " " + password);
        String user_path = PATH_USERS + username + ".txt";
        System.out.println("BoulderDashServerImpl - var userpath: " + user_path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(user_path)));
            String file_password = br.readLine();
            if (file_password.compareTo(password) == 0) {
                client.sendMessage("Your password is correct!");
                System.out.println("BoulderDashServerImpl - login(): " + username + " is now logged in.");
                clients.add(client);
                bdsGUI.addConnectedClient(username, clients.size());
                return 1;
            } else {
                client.sendMessage("Your password is incorrect!");
                System.out.println("BoulderDashServerImpl - login(): " + username + " has failed the password.");
                return 0;
            }

        } catch (FileNotFoundException ex) {
            client.sendMessage("Your account is not found, going to register this one.");
            return 0;
        } catch (IOException ex) {
            return 0;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized int logout(String username) throws RemoteException {
        System.out.println("BoulderDashServerImpl - logout(): " + username);
        for (BoulderDashClientRI user : this.clients) {
            if (user.getClientUsername().compareTo(username) == 0) {
                clients.remove(user);
                bdsGUI.removeConnectedClient(username, clients.size());
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int register(BoulderDashClientRI client, String username, String password) throws RemoteException {
        String user_path = PATH_USERS + username + ".txt";
        File f = new File(user_path);

        if (f.exists()) {
            client.sendMessage("This username is already registed!");
            return 0;
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(new File(user_path)));
            bw.write(password);
        } catch (IOException e) {
            Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                    client.sendMessage("Your account has been created!");
                }
            } catch (IOException e) {
                Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 1;
    }

    @Override
    public void attach(BoulderDashClientRI client) throws RemoteException {
        this.clients.add(client);
    }

    @Override
    public void detach(BoulderDashClientRI client) throws RemoteException {
        this.clients.remove(client);
    }

    @Override
    public Object getState() throws RemoteException {
        return this.state;
    }

    @Override
    public void setState(Object s) throws RemoteException {
        System.out.println("BoulderDashServerImpl - setState()");
        this.state = s;
        notifyAllObservers();
    }

    public void notifyAllObservers() throws RemoteException {
        System.out.println("BoulderDashServerImpl - notifyAllObservers()");
        for (BoulderDashClientRI client : clients) {
            client.update();
        }
    }

    @Override
    public int countConnectedClients() throws RemoteException {
        return this.clients.size();
    }

    public void shutdown() {
        System.out.println("SHUTDOWN SERVER");
        System.exit(0);
    }
}
