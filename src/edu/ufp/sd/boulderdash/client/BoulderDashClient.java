package edu.ufp.sd.boulderdash.client;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.ufp.sd.boulderdash.server.BoulderDashServerRI;

/**
 * <p>
 * Title: Projecto SD - BoulderDash</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Tiago Cardoso <tiagocardosoweb@gmail.com>
 * @author Miguel Ferreira <migueelfsf@gmail.com>
 * @version 1.0
 */
public class BoulderDashClient {

    //public static String serviceName = "rmi://localhost:1099/BoulderDashService";
    public BoulderDashClient(String[] args) {
        if (args == null) {
            System.out.println("BoulderDashClient - Constructor(): Args null");
            System.exit(0);
        }

        try {
            //Check args for receiving hostname
            String registryHostname = "localhost"; //May be an IP or hostname
            int registryPort = 1099; //Default port is 1099
            String serviceHostname = "localhost"; //May be an IP or hostname
            int servicePort = 1099; //Default port is 1099
            String serviceName = "rmi://localhost:1099/BoulderDashService";

            if (args.length < 2) {
                System.err.println("usage: java [options] edu.ufp.sd.boulderdash.client.BoulderDashClient <server_rmi_hostname/ip> <server_rmi_port>");
                System.exit(1);
            } else {
                registryHostname = args[0];
                serviceHostname = args[0];
                servicePort = Integer.parseInt(args[1]);
                serviceName = "rmi://" + args[0] + ":1099/BoulderDashService";
            }

            // Create and install a security manager
            if (System.getSecurityManager() == null) {
                System.out.println("BoulderDashClient - Constructor(): set security manager");
                System.setSecurityManager(new SecurityManager());
            }

            //Get proxy to Registry service
            Registry registry = LocateRegistry.getRegistry(registryHostname, registryPort);
            if (registry == null) {
                System.out.println("BoulderDashClient - Constructor(): registry is null!!");
            } else {
                String[] rmiServersList = registry.list();
                System.out.println("BoulderDashClient - Constructor(): rmiServersList.length = " + rmiServersList.length);
                for (int i = 0; i < rmiServersList.length; i++) {
                    System.out.println("BoulderDashClient - Constructor(): rmiServersList[" + i + "] = " + rmiServersList[i]);
                }

                //String serviceName = "rmi://"+inetAddr.getHostAddress()+":1099/BoulderDashService";
                //String serviceName = System.getProperty("edu.ufp.sd.boulderdash.servicename");
                System.out.println("BoulderDashClient - Constructor(): going to lookup service " + serviceName + "...");

                //Get proxy to BoulderDash service
                BoulderDashServerRI bdsRI = (BoulderDashServerRI) registry.lookup(serviceName);
                BoulderDashClientImpl bdImlp = new BoulderDashClientImpl(bdsRI);

                //Call BoulderDashServer remote service
                System.out.println("BoulderDashClient - Constructor(): after calling service " + serviceName + "...");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(BoulderDashClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(BoulderDashClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; args != null && i < args.length; i++) {
            System.out.println("BoulderDashClient - main(): args[" + i + "] = " + args[i]);
        }
        BoulderDashClient bdc = new BoulderDashClient((args != null && args.length > 0 ? args : null));
    }

}
