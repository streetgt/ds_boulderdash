package fr.enssat.BoulderDash.controllers;

import fr.enssat.BoulderDash.models.LevelModel;
import fr.enssat.BoulderDash.helpers.AudioLoadHelper;
import fr.enssat.BoulderDash.views.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameController
 *
 * This system creates the view. The game loop is also handled there.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class GameController implements ActionListener {

    private LevelModel levelModel;
    private AudioLoadHelper audioLoadHelper;
    private boolean firstClickOnPause;
    private GameView gameView;

   /**
    * Class constructor
    */
    public GameController()
    {
        this.firstClickOnPause = true;

        this.audioLoadHelper = new AudioLoadHelper();
        this.levelModel = new LevelModel("level01", audioLoadHelper);
        this.gameView = new GameView(this, levelModel);
     
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
            case "pause":
                if (this.firstClickOnPause) {
                    this.levelModel.setGamePaused(true);
                } else if (!this.firstClickOnPause) {
                    this.levelModel.setGamePaused(false);
                }

                this.firstClickOnPause = !this.firstClickOnPause;
                this.gameView.getGameFieldView().grabFocus();
                break;

            case "restart":
                this.resetGame("restart");
                this.getAudioLoadHelper().playSound("new");
                this.gameView.getGameFieldView().grabFocus();
                break;

            case "menu":
                //this.menuView.setVisible(true);
                //this.getAudioLoadHelper().startMusic("game");
                //this.resetGame("menu");
                break;
        }
    }

    /**
     * Function to reset the game
     */
    private void resetGame(String source) {
        this.gameView.dispose();

        if (source.equals("restart")) {
            this.levelModel = new LevelModel("level01", audioLoadHelper);
            this.gameView = new GameView(this, levelModel);
            this.gameView.setVisible(true);
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
}
