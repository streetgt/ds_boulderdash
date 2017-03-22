package fr.enssat.BoulderDash.views;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.enssat.BoulderDash.models.LevelModel;

/**
 * InformationPanel
 *
 * Information panel element.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-20
 */
public class InformationPanel extends JPanel implements Observer {

    private LevelModel levelModel;
    private JTextArea text;

    /**
     * Class constructor
     */
    public InformationPanel(LevelModel levelModel) {
        this.levelModel = levelModel;
        this.text = new JTextArea();
        this.text.setEditable(false);
        this.levelModel.getGameInformationModel().addObserver(this);

        this.text.setText(
                "Rockford 0 - Score : " + levelModel.getGameInformationModel().getScore(0) + "\n"
                + "Rockford 1 - Score : " + levelModel.getGameInformationModel().getScore(1)
                + "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingsDiamonds()
        );

        this.add(this.text);
    }

    /**
     * Updates the panel
     *
     * @param o Observable item
     * @param arg Object item
     */
    @Override
    public void update(Observable o, Object arg) {
        this.text.setText(
                "Rockford 0 - Score : " + levelModel.getGameInformationModel().getScore(0) + "\n"
                + "Rockford 1 - Score : " + levelModel.getGameInformationModel().getScore(1)
                + "\nRemaining diamonds : " + this.levelModel.getGameInformationModel().getRemainingsDiamonds()
        );
    }
}
