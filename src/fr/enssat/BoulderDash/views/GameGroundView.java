package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.views.GroundView;
import fr.enssat.BoulderDash.controllers.GameController;
import fr.enssat.BoulderDash.controllers.GameKeyController;
import fr.enssat.BoulderDash.models.LevelModel;

import javax.swing.*;
import java.awt.*;

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
    private int serverID;

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel Level model
     */
    public GameGroundView(BoulderDashClientImpl bdc, GameController gameController, LevelModel levelModel, int serverID) {
        super(levelModel);

        this.bdc = bdc;

        this.gameController = gameController;
        this.serverID = serverID;

        this.addKeyListener(new GameKeyController(this.bdc, this.levelModel, this.gameController.getAudioLoadHelper(), serverID));

        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setFocusable(true);
    }
}
