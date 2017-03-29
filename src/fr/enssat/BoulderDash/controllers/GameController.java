package fr.enssat.BoulderDash.controllers;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.models.LevelModel;
import fr.enssat.BoulderDash.helpers.AudioLoadHelper;
import fr.enssat.BoulderDash.views.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private boolean firstClickOnPause;
    private GameView gameView;
    private int roomID;

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
        this.firstClickOnPause = true;
        this.audioLoadHelper = new AudioLoadHelper();
        this.gameView = new GameView(this.bdc, this, this.roomID);

        // Play new song
        this.getAudioLoadHelper().playSound("new");

        this.getGameView().setVisible(true);
        this.getGameView().getGameFieldView().grabFocus();
    }

    /**
     * Handles the 'action performed' event
     *
     * @param event Action event
     */
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "pause": {
//                if (this.firstClickOnPause) {
//                    this.levelModel.setGamePaused(true);
//                } else if (!this.firstClickOnPause) {
//                    this.levelModel.setGamePaused(false);
//                }
//
//                this.firstClickOnPause = !this.firstClickOnPause;
//                this.gameView.getGameFieldView().grabFocus();
                break;
            }
            case "exit": {
                try {
                    this.bdc.getBdsRI().removeClientFromRoom(bdc, roomID);
                    this.bdc.getBdcHallUI().newRoomButtonClickable(true);
                    this.gameView.dispose();
                    this.finalize();
                } catch (RemoteException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Throwable ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

//            case "exit":
//                this.resetGame("restart");
//                this.getAudioLoadHelper().playSound("new");
//                this.gameView.getGameFieldView().grabFocus();
//                break;
//
//            case "menu":
//                //this.menuView.setVisible(true);
//                //this.getAudioLoadHelper().startMusic("game");
//                //this.resetGame("menu");
//                break;
        }
    }

//    /**
//     * Function to reset the game
//     */
//    private void resetGame(String source) {
//        this.gameView.dispose();
//
//        if (source.equals("restart")) {
//            this.levelModel = new LevelModel("level01", audioLoadHelper);
//            this.gameView = new GameView(this.bdc, this, levelModel, roomID);
//            this.gameView.setVisible(true);
//        }
//    }

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
}
