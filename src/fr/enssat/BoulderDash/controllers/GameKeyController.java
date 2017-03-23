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
    private LevelModel levelModel;
    private RockfordUpdateController updatePosRockford;

    /**
     * Class constructor
     *
     * @param levelModel Level model
     */
    public GameKeyController(BoulderDashClientImpl bdc, LevelModel levelModel, AudioLoadHelper audioLoadHelper) {
        this.bdc = bdc;
        if(this.bdc == null) {
            System.out.println("FOUND FUCKING NULL!");
        }
        this.levelModel = levelModel;
        new BoulderAndDiamondController(levelModel, audioLoadHelper);
        this.updatePosRockford = new RockfordUpdateController(levelModel);
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
                    this.bdc.getBdsRI().sendKeys(bdc, "UP");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                DisplayableElementModel upElement = levelModel.getGroundLevelModel()[levelModel.getRockford(0).getPositionX()][levelModel.getRockford(0).getPositionY() - 1];

                if (upElement.getPriority() < levelModel.getRockford(0).getPriority()) {
                    this.updatePosRockford.moveRockford(0, levelModel.getRockford(0).getPositionX(), levelModel.getRockford(0).getPositionY() - 1);
                    this.levelModel.getRockford(0).startRunningUp();
                }

//                DisplayableElementModel wElement = levelModel.getGroundLevelModel()[levelModel.getRockford(1).getPositionX()][levelModel.getRockford(1).getPositionY() - 1];
//
//                if (wElement.getPriority() < levelModel.getRockford(1).getPriority()) {
//                    this.updatePosRockford.moveRockford(1, levelModel.getRockford(1).getPositionX(), levelModel.getRockford(1).getPositionY() - 1);
//                    this.levelModel.getRockford(1).startRunningUp();
//                }

                break;
            }

            // Direction Rockford 1: DOWN
            case KeyEvent.VK_DOWN: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, "DOWN");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                DisplayableElementModel downElement = levelModel.getGroundLevelModel()[levelModel.getRockford(0).getPositionX()][levelModel.getRockford(0).getPositionY() + 1];

                if (downElement.getPriority() < levelModel.getRockford(0).getPriority()) {
                    this.updatePosRockford.moveRockford(0, levelModel.getRockford(0).getPositionX(), levelModel.getRockford(0).getPositionY() + 1);
                    this.levelModel.getRockford(0).startRunningDown();
                }
//
//                DisplayableElementModel sElement = levelModel.getGroundLevelModel()[levelModel.getRockford(1).getPositionX()][levelModel.getRockford(1).getPositionY() + 1];
//
//                if (sElement.getPriority() < levelModel.getRockford(1).getPriority()) {
//                    this.updatePosRockford.moveRockford(1, levelModel.getRockford(1).getPositionX(), levelModel.getRockford(1).getPositionY() + 1);
//                    this.levelModel.getRockford(1).startRunningDown();
//                }

                break;
            }

            // Direction Rockford 1: LEFT
            case KeyEvent.VK_LEFT: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, "LEFT");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                DisplayableElementModel leftElement = levelModel.getGroundLevelModel()[levelModel.getRockford(0).getPositionX() - 1][levelModel.getRockford(0).getPositionY()];

                if (leftElement.getPriority() < levelModel.getRockford(0).getPriority()) {
                    this.updatePosRockford.moveRockford(0, levelModel.getRockford(0).getPositionX() - 1, levelModel.getRockford(0).getPositionY());
                    this.levelModel.getRockford(0).startRunningLeft();
                }

//                DisplayableElementModel aElement = levelModel.getGroundLevelModel()[levelModel.getRockford(1).getPositionX() - 1][levelModel.getRockford(1).getPositionY()];
//
//                if (aElement.getPriority() < levelModel.getRockford(1).getPriority()) {
//                    this.updatePosRockford.moveRockford(1, levelModel.getRockford(1).getPositionX() - 1, levelModel.getRockford(1).getPositionY());
//                    this.levelModel.getRockford(1).startRunningLeft();
//                }

                break;
            }

            // Direction Rockford 1: RIGHT
            case KeyEvent.VK_RIGHT: {
                try {
                    this.bdc.getBdsRI().sendKeys(bdc, "RIGHT");
                } catch (RemoteException ex) {
                    Logger.getLogger(GameKeyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                DisplayableElementModel rightElement = levelModel.getGroundLevelModel()[levelModel.getRockford(0).getPositionX() + 1][levelModel.getRockford(0).getPositionY()];

                if (rightElement.getPriority() < levelModel.getRockford(0).getPriority()) {
                    this.updatePosRockford.moveRockford(0, levelModel.getRockford(0).getPositionX() + 1, levelModel.getRockford(0).getPositionY());
                    this.levelModel.getRockford(0).startRunningRight();
                }

//                DisplayableElementModel dElement = levelModel.getGroundLevelModel()[levelModel.getRockford(1).getPositionX() + 1][levelModel.getRockford(1).getPositionY()];
//
//                if (dElement.getPriority() < levelModel.getRockford(1).getPriority()) {
//                    this.updatePosRockford.moveRockford(1, levelModel.getRockford(1).getPositionX() + 1, levelModel.getRockford(1).getPositionY());
//                    this.levelModel.getRockford(1).startRunningRight();
//                }

                break;
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
            case KeyEvent.VK_LEFT:
                this.levelModel.getRockford(0).startStaying();
                this.levelModel.getRockford(1).startStaying();
                break;
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
