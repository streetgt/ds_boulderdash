package fr.enssat.BoulderDash.controllers;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.models.DisplayableElementModel;
import fr.enssat.BoulderDash.models.LevelModel;
import fr.enssat.BoulderDash.helpers.AudioLoadHelper;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameKeyController
 *
 * Manages the key events controller.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class GameKeyController implements KeyListener {

    private BoulderDashClientImpl bdc;
    private int serverID;

    /**
     * Class constructor
     *
     * @param levelModel Level model
     */
    public GameKeyController(BoulderDashClientImpl bdc, AudioLoadHelper audioLoadHelper, int serverID) {
        this.bdc = bdc;
        if (this.bdc == null) {
            System.out.println("FOUND FUCKING NULL!");
        }
        this.serverID = serverID;
    }

    /**
     * Handles the 'key pressed' event
     *
     * @param e Key event
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        System.out.println("[DEBUG]: Pressed keyCode " + keyCode);
        switch (keyCode) {
            // Direction Rockford 1: UP
            case KeyEvent.VK_UP: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, serverID, "UP");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }

            // Direction Rockford 1: DOWN
            case KeyEvent.VK_DOWN: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, serverID, "DOWN");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            // Direction Rockford 1: LEFT
            case KeyEvent.VK_LEFT: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, serverID, "LEFT");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            // Direction Rockford 1: RIGHT
            case KeyEvent.VK_RIGHT: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, serverID, "RIGHT");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * Handles the 'key released' event
     *
     * @param e Key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, serverID, "STAYING");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

    }

    /**
     * Handles the 'key typed' event
     *
     * @param e Key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing.
    }
}
