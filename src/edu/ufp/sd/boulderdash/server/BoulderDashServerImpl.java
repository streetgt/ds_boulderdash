package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public class BoulderDashServerImpl extends UnicastRemoteObject implements BoulderDashServerRI {

    private BoulderDashServerGUI bdsGUI;
    
    private ArrayList<BoulderDashClientRI> users = new ArrayList<>();
    
    public static String PATH_USERS  = "../../data/users/";
    
    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public BoulderDashServerImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
        this.bdsGUI = new BoulderDashServerGUI(this);
    }

    public void print(String msg) throws RemoteException {
        System.out.println("BoulderDashServerImpl: print(\"" + msg +"\")");
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
            if(file_password.compareTo(password) == 0) {
                client.sendMessage("Your password is correct!");
                System.out.println("BoulderDashServerImpl - login(): password correct");
                users.add(client);
                bdsGUI.setConnectedPlayers(users.size());
                bdsGUI.addConnectedUser(username);
                return 1;
            } else {
                client.sendMessage("Your password is incorrect!");
                System.out.println("BoulderDashServerImpl - login(): password incorrect");
                return 0;
            }
            
        } catch (FileNotFoundException ex) {
            client.sendMessage("Your account is not found!");
            return 0;
        } catch (IOException ex) {
            return 0;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized int logout(String username) throws RemoteException {
        System.out.println("BoulderDashServerImpl - logout(): " + username);
        for (BoulderDashClientRI user : users) {
            if(user.getUsername().compareTo(username) == 0) {
                users.remove(user);
                bdsGUI.setConnectedPlayers(users.size());
                bdsGUI.removeConnectedUser(username);
                return 1;
            }
        }
        return 0;
    }
    
    
    public void shutdown() {
        System.out.println("SHUTDOWN SERVER");
        System.exit(0);
    }
}
