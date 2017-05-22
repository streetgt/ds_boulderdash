package fr.enssat.BoulderDash.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class WinLoseView extends JFrame {

    private String name;
    private String winOrLose;

    /**
     * Generate the WinLoseView
     */
    public WinLoseView(String name, String winOrLose) {
        this.name = name;
        this.winOrLose = winOrLose;
        this.initializeView();
        this.createLayout();
    }

    /**
     * Initializes the view
     */
    private void initializeView() {
        this.setVisible(true);
        this.setResizable(false);

        // UI parameters
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setBounds(300, 300, 250, 100);

        // App parameters
        this.setTitle("END OF THE GAME ! ");
    }

    /**
     * Creates the view layout
     */
    private void createLayout() {
        JTextArea help = new JTextArea();
        help.setEditable(false);
        if (winOrLose.equals("win")) {
            help.setText("You have won the game!".toUpperCase());
        } else {
            help.setText("Player " + name + " has won the game!".toUpperCase());
        }

        this.add(help);
    }
}
