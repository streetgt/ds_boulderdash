package edu.ufp.sd.boulderdash.server;

import edu.ufp.sd.boulderdash.server.game.ThreadPool;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.ufp.sd.boulderdash.server.game.LevelModelRoom;

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
    
    private ThreadPool threadPool;
    private BoulderDashServerGUI bdsGUI;
    
    protected ArrayList<BoulderDashClientRI> clients = new ArrayList<>();
    protected ArrayList<LevelModelRoom> rooms = new ArrayList<>();
    
    public static String PATH_USERS = "../../data/users/";
    public static String PATH_LEVELS = "../../res/levels/";

    // Uses RMI-default sockets-based transport
    // Runs forever (do not passivates) - Do not needs rmid (activation deamon)
    // Constructor must throw RemoteException due to export()
    public BoulderDashServerImpl() throws RemoteException {
        // Invokes UnicastRemoteObject constructor which exports remote object
        super();
        this.bdsGUI = new BoulderDashServerGUI(this);
        this.threadPool = new ThreadPool(2);
    }
    
    @Override
    public void print(String msg) throws RemoteException {
        System.out.println("BoulderDashServerImpl: print(\"" + msg + "\")");
    }
    
    @Override
    public synchronized int login(BoulderDashClientRI client, String username, String password) throws RemoteException {
        System.out.println("BoulderDashServerImpl - login(): " + username + " " + password);
        if (clientAlreadyLoggedin(username)) {
            System.out.println("BoulderDashServerImpl - login(): " + username + " is already logged in.");
            return 0;
        }
        
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
        if (!clients.isEmpty()) {
            notifyAllObservers();
        }
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
    
    @Override
    public int createGameRoom(BoulderDashClientRI client, String level) throws RemoteException {
        LevelModelRoom newServer = new LevelModelRoom(level);
        this.rooms.add(newServer);
        newServer.setRoomID(rooms.indexOf(newServer));
        String name = rooms.size() - 1 + "# BoulderDash Room"
                + " - Level: " + level
                + " - Players: " + newServer.getClients().size() + "/2";
        newServer.setRoomName(name);
        //newServer.getClients().add(client);
        this.bdsGUI.addRoomToList(name);
        this.setState(new State().new NewRoom(false, name));
        return rooms.size() - 1;
    }
    
    public void createGameRoom() throws RemoteException {
        String level = "level01";
        LevelModelRoom newServer = new LevelModelRoom(level);
        this.rooms.add(newServer);
        newServer.setRoomID(rooms.indexOf(newServer));
        String name = rooms.indexOf(newServer) + "# BoulderDash Room"
                + " - Level: " + level
                + " - Players: " + newServer.getClients().size() + "/2";
        newServer.setRoomName(name);
        this.bdsGUI.addRoomToList(name);
        this.setState(new State().new NewRoom(false, name));
    }
    
    @Override
    public String[] fetchAvaliableLevels() throws RemoteException {
        List<String> stockList = new ArrayList<>();
        
        File directory = new File(PATH_LEVELS);
        File[] fileList = directory.listFiles();
        String fileName, fileNameExtValue;
        int fileNameExtIndex;
        
        for (File file : fileList) {
            fileName = file.getName();
            fileNameExtIndex = fileName.lastIndexOf('.');
            
            if (fileNameExtIndex > 0) {
                fileNameExtValue = fileName.substring(fileNameExtIndex, fileName.length());
                
                if (fileNameExtValue.equals(".xml")) {
                    fileName = fileName.substring(0, fileNameExtIndex);
                    System.out.println(fileName);
                    stockList.add(fileName);
                }
            }
        }

        // Convert to String[] (required)
        String[] itemsArr = new String[stockList.size()];
        itemsArr = stockList.toArray(itemsArr);
        
        return itemsArr;
    }
    
    @Override
    public String[] fetchAvaliableRooms() throws RemoteException {
        
        String[] itemsArr = new String[rooms.size()];
        if (!rooms.isEmpty()) {
            for (int i = 0; i < rooms.size(); i++) {
                itemsArr[i] = rooms.get(i).getRoomName();
            }
        }
        
        return itemsArr;
    }
    
    @Override
    public void sendKeys(BoulderDashClientRI client, int roomID, String direction) throws RemoteException {
        threadPool.execute(new SendKeysRunnable(client, this.rooms.get(roomID), direction));
    }
    
    private boolean clientAlreadyLoggedin(String username) {
        System.out.println("clientAlreadyLoggedin: " + username);
        try {
            for (BoulderDashClientRI client : clients) {
                if (client.getClientUsername().compareTo(username) == 0) {
                    return true;
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public ArrayList<LevelModelRoom> getRooms() {
        return rooms;
    }
    
    public void setRooms(ArrayList<LevelModelRoom> rooms) {
        this.rooms = rooms;
    }
    
    public BoulderDashClientRI clientFromUsername(String username) {
        try {
            for (BoulderDashClientRI client : clients) {
                if (client.getClientUsername().compareTo(username) == 0) {
                    return client;
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int getRoomIndexByName(String name) {
        for (LevelModelRoom room : rooms) {
            if (room.getRoomName().compareTo(name) == 0) {
                return rooms.indexOf(room);
            }
        }
        
        return -1;
    }
    
    public void shutdown() {
        System.out.println("SHUTDOWN SERVER");
        System.exit(0);
    }
    
    @Override
    public boolean addClientToRoom(BoulderDashClientRI client, int roomID) throws RemoteException {
        LevelModelRoom server = null;
        try {
            server = this.rooms.get(roomID);
            server.addClient(client);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        
        return false;
    }
    
    @Override
    public void removeClientFromRoom(BoulderDashClientRI client, int roomID) throws RemoteException {
        LevelModelRoom server = null;
        try {
            server = this.rooms.get(roomID);
            server.removeClient(client);
        } catch (IndexOutOfBoundsException ex) {
            Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getClientNameInRoom(int roomID, int index) throws RemoteException {
        LevelModelRoom server = null;
        BoulderDashClientRI client = null;
        try {
            server = this.rooms.get(roomID);
            client = server.getClients().get(index);
            if (client != null) {
                return client.getClientUsername();
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        
        return null;
    }
    
    @Override
    public int getClientScoreInRoom(int roomID, int index) throws RemoteException {
        LevelModelRoom server = null;
        int val = -1;
        try {
            server = this.rooms.get(roomID);
            val = server.getGameInformationModel().getScore(index);
            if (val != -1) {
                return val;
            }
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
        
        return -1;
    }
    
    @Override
    public int getRoomRemainingDiamonds(int roomID) throws RemoteException {
        LevelModelRoom server = null;
        int val = -1;
        try {
            server = this.rooms.get(roomID);
            val = server.getGameInformationModel().getRemainingsDiamonds();
            if (val != -1) {
                return val;
            }
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
        
        return -1;
    }
    
    @Override
    public int[] getRoomMapSize(int roomID) throws RemoteException {
        //System.out.println("getRoomMapSize(" + roomID + ")");
        LevelModelRoom server = null;
        int[] size = null;
        try {
            server = this.rooms.get(roomID);
            size = new int[2];
            size[0] = server.getSizeWidth();
            size[1] = server.getSizeHeight();
        } catch (IndexOutOfBoundsException ex) {
            Logger.getLogger(BoulderDashServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return size;
    }
    
}

class SendKeysRunnable implements Runnable {

    private BoulderDashClientRI client;
    private LevelModelRoom server;
    private String movement;
    
    public SendKeysRunnable(BoulderDashClientRI client, LevelModelRoom server, String movement) {
        this.client = client;
        this.server = server;
        this.movement = movement;
    }
    
    @Override
    public void run() {
        System.out.println("Sending movement using Thread: " + Thread.currentThread().getId());
        this.server.moveRockford(client,movement);
    } 
}
