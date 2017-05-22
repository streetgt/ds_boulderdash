package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import fr.enssat.BoulderDash.controllers.GameController;

import javax.swing.JPanel;
import javax.swing.JTextArea;

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
public class InformationPanel extends JPanel {

    private BoulderDashClientImpl bdc;

    private GameController gameController;
    private JTextArea text;
    private int roomID;
    private String[] players = {"Not Connected", "Not Connected"};

    /**
     * Class constructor
     */
    public InformationPanel(BoulderDashClientImpl bdc, GameController gameController, int roomID) {
        this.bdc = bdc;
        this.gameController = gameController;
        this.text = new JTextArea();
        this.text.setEditable(false);

        updateInformation();

        this.add(this.text);
    }

    /**
     * Updates the InformationPanel
     */
    public void updateInformation() {
        System.out.println("updateInformation() executed");
        try {

            for (int i = 0; i < players.length; i++) {
                String name = bdc.getBdsRI().getClientNameInRoom(roomID, i);
                if (name != null) {
                    players[i] = name;
                }
            }

            this.text.setText(
                    players[0] + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 0) + "\n"
                    + players[1] + " - Score : " + bdc.getBdsRI().getClientScoreInRoom(roomID, 1)
                    + "\nRemaining diamonds : " + bdc.getBdsRI().getRoomRemainingDiamonds(roomID)
            );
        } catch (RemoteException ex) {
            Logger.getLogger(InformationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
