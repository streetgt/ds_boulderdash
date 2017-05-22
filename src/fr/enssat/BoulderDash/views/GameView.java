package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;
import javax.swing.*;

import java.awt.*;

import fr.enssat.BoulderDash.controllers.GameController;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameView
 *
 * Specifies the game view itself.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class GameView extends JFrame implements WindowListener {

    private BoulderDashClientImpl bdc;
    private GameGroundView gameGroundView;
    private JPanel actionPanel;
    private InformationPanel informationPanel;
    private GameController gameController;
    private int roomID;

    /**
     * Class constructor
     *
     * @param gameController Game controller
     * @param levelModel Level model
     */
    public GameView(BoulderDashClientImpl bdc, GameController gameController, int roomID) {
        this.bdc = bdc;
        this.gameController = gameController;
        this.roomID = roomID;

        this.initializeView();
        this.createLayout();

        this.gameGroundView.grabFocus();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(false);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 432, 536);

        // App parameters
        this.setTitle("Boulder Dash | Game - Multiplayer");

        Image appIcon = Toolkit.getDefaultToolkit().getImage("./res/app/app_icon.png");
        this.setIconImage(appIcon);
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        this.gameGroundView = new GameGroundView(this.bdc, this.gameController, roomID);
        this.actionPanel = new JPanel();
        this.informationPanel = new InformationPanel(this.bdc, this.gameController, this.roomID);
        this.informationPanel.setBackground(Color.white);

        // Add some buttons on the informationPanel
        this.createButton("pause", "Pause");
        this.createButton("exit", "Exit");

        this.add(this.informationPanel, BorderLayout.NORTH);
        this.add(this.gameGroundView, BorderLayout.CENTER);
        this.add(this.actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Gets the game field view
     *
     * @return Game field view
     */
    public GameGroundView getGameFieldView() {
        return this.gameGroundView;
    }

    public InformationPanel getInformationPanel() {
        return informationPanel;
    }

    public void setInformationPanel(InformationPanel informationPanel) {
        this.informationPanel = informationPanel;
    }

    /**
     * Creates the given button
     *
     * @param name Button name
     * @return Created button
     */
    public JButton createButton(String id, String name) {
        JButton button = new JButton(name);
        button.addActionListener(this.gameController);
        button.setActionCommand(id);

        this.actionPanel.add(button);

        return button;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            if (this.bdc.isPlaying()) {
                this.bdc.setPlaying(false);
                this.bdc.getBdsRI().removeClientFromRoom(bdc, roomID);
            }
            this.bdc.getBdcHallUI().newRoomButtonClickable(true);
            this.dispose();
        } catch (RemoteException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
