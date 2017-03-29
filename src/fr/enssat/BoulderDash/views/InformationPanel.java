package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
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

    private BoulderDashClientImpl bdc;

    private JTextArea text;
    private int roomID;

    /**
     * Class constructor
     */
    public InformationPanel(BoulderDashClientImpl bdc, int roomID) {
        this.bdc = bdc;
        this.text = new JTextArea();
        this.text.setEditable(false);
      
        try {
            this.text.setText(
                    bdc.getBdsRI().getClientNameInRoom(roomID, 0) + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 0) + "\n"
                    + bdc.getBdsRI().getClientNameInRoom(roomID, 1) + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 1)
                    + "\nRemaining diamonds : " + bdc.getBdsRI().getRoomRemainingDiamonds(roomID)
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
                    bdc.getBdsRI().getClientNameInRoom(roomID, 0) + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 0) + "\n"
                    + bdc.getBdsRI().getClientNameInRoom(roomID, 1) + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 1)
                    + "\nRemaining diamonds : " + bdc.getBdsRI().getRoomRemainingDiamonds(roomID)
            );
        } catch (RemoteException ex) {
            Logger.getLogger(InformationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
