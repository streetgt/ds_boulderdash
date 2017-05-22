package fr.enssat.BoulderDash.controllers;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.models.LevelModel;
import fr.enssat.BoulderDash.helpers.AudioLoadHelper;
import fr.enssat.BoulderDash.views.GameView;
import fr.enssat.BoulderDash.views.WinLoseView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * GameController
 *
 * This system creates the view. The game loop is also handled there.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class GameController implements ActionListener {

    private BoulderDashClientImpl bdc;
    private AudioLoadHelper audioLoadHelper;
    private GameView gameView;
    private int roomID;
    private JLabel centerLabel;

    private boolean gameEnded = false;

    /**
     * Class constructor
     */
    public GameController(BoulderDashClientImpl bdc, int roomID) {
        this.bdc = bdc;
        this.roomID = roomID;
        if (this.bdc == null) {
            System.out.println("GameController bdc = null");
        }
        this.bdc.setPlaying(true);

        // Gameview
        this.gameView = new GameView(this.bdc, this, this.roomID);
        this.getGameView().setVisible(true);
        this.getGameView().getGameFieldView().grabFocus();

        // Waiting text
        this.centerLabel = new JLabel("Waiting for a player to join ...", JLabel.CENTER);
        this.centerLabel.setVisible(true);
        this.gameView.add(centerLabel);

        // Song
        this.audioLoadHelper = new AudioLoadHelper();
        //this.audioLoadHelper.startMusic("game");

        // GameController
        this.bdc.setGameController(this);

    }

    /**
     * Handles the 'action performed' event
     *
     * @param event Action event
     */
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "exit": {
                if (gameEnded) {
                    try {
                        this.bdc.getBdsRI().removeClientFromRoom(bdc, roomID);
                        this.bdc.getBdcHallUI().newRoomButtonClickable(true);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                this.gameView.dispose();
                try {
                    this.finalize();
                } catch (Throwable ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
        }
    }

    /**
     * Gets the audio load helper instance
     *
     * @return Audio load helper instance
     */
    public AudioLoadHelper getAudioLoadHelper() {
        return this.audioLoadHelper;
    }

    /**
     * Return the game view
     *
     * @return gameView
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Set the gameView
     *
     * @param gameView
     */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
    
    /**
     * Set the view to inform the user that he won
     */
    public void displayWin(String name) {
        new WinLoseView(name, "win");
    }

    /**
     * Set the view to inform the user that he is not good at this game
     */
    public void displayLose(String name) {
        new WinLoseView(name, "loose");
    }
    
}
