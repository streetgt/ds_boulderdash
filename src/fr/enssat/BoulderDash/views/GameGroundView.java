package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.controllers.GameController;
import fr.enssat.BoulderDash.controllers.GameKeyController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * GameFieldView
 *
 * Game field view for the game itself.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-21
 */
public class GameGroundView extends GroundView {

    private BoulderDashClientImpl bdc;
    private GameController gameController;
    private int roomID;

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel Level model
     */
    public GameGroundView(BoulderDashClientImpl bdc, GameController gameController, int roomID) {
        super(bdc, roomID);

        this.bdc = bdc;

        this.gameController = gameController;
        this.roomID = roomID;

        initCustom();
    }

    private void initCustom() {
        this.addKeyListener(new GameKeyController(this.bdc, this.gameController.getAudioLoadHelper(), roomID));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFocusable(true); 
    }
}
