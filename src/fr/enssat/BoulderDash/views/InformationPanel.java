package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClient;
import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.enssat.BoulderDash.models.LevelModel;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InformationPanel
 *
 * Information panel element.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-20
 */
public class InformationPanel extends JPanel implements Observer {

    private BoulderDashClientRI bdc;
    private LevelModel levelModel;
    private JTextArea text;
    private int serverID;

    /**
     * Class constructor
     */
    public InformationPanel(BoulderDashClientRI bdc, LevelModel levelModel) {
        this.bdc = bdc;
        this.levelModel = levelModel;
        this.text = new JTextArea();
        this.text.setEditable(false);
        this.levelModel.getGameInformationModel().addObserver(this);

        try {
            this.text.setText(
                    bdc.getClientUsername() + " - Score : " + levelModel.getGameInformationModel().getScore(0) + "\n"
                    + bdc.getClientUsername() + " - Score : " + levelModel.getGameInformationModel().getScore(1)
                    + "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingsDiamonds()
            );
        } catch (RemoteException ex) {
            Logger.getLogger(InformationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        try {
            this.text.setText(
                    bdc.getClientUsername() + " - Score : " + levelModel.getGameInformationModel().getScore(0) + "\n"
                    + bdc.getClientUsername() + " - Score : " + levelModel.getGameInformationModel().getScore(1)
                    + "\nRemaining diamonds : " + levelModel.getGameInformationModel().getRemainingsDiamonds()
            );
        } catch (RemoteException ex) {
            Logger.getLogger(InformationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
