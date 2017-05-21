package fr.enssat.BoulderDash.views;

import edu.ufp.sd.boulderdash.client.BoulderDashClientImpl;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * FieldView
 *
 * FieldView, created by controller; we notice that we don't need to make
 * levelModel observable; Because of the sprites we have to refresh the game
 * windows very often so don't need of observers/observable mechanism
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 *
 * This view is basically drawing into the Frame the levelModel.
 *
 */
public abstract class GroundView extends JPanel {

    private static String spriteStorageFolderPath = "../../res/drawable/field/";
    private BoulderDashClientImpl bdc;
    private int roomID;
    private String[][] levelSprites = null;
  
    /**
     * Class constructor
     *
     * @param levelModel Level model
     */
    public GroundView(BoulderDashClientImpl bdc, int roomID) {
        this.bdc = bdc;
        this.roomID = roomID; 
    }

    /**
     * Draws the map
     *
     * @param width Map width
     * @param height Map height
     * @param g Map graphical object
     */
    public void drawTerrain(String[][] levelSprites, int width, int height, Graphics g) {
        System.out.println("drawTerrain");
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                g.drawImage(this.loadSprite(levelSprites[x][y]), (x * 16), (y * 16), this);
            }
        }
    }

    /**
     * Set the view to inform the user that he won
     */
    private void displayWin(int index) {
        new WinLoseView(index, "win");
    }

    /**
     * Set the view to inform the user that he is not good at this game
     */
    private void displayLose(int index) {
        new WinLoseView(index, "loose");
    }

    /**
     * Paints the map
     *
     * @param g Map graphical object
     */
    @Override
    public void paint(Graphics g) {
        if (this.levelSprites != null) {
            this.drawTerrain(this.levelSprites, this.levelSprites.length, this.levelSprites.length, g);
        }
    }

    /**
     * Loads the target sprite
     *
     * @param spriteName Sprite name
     * @return Sprite object
     */
    private BufferedImage loadSprite(String spriteName) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(spriteStorageFolderPath + spriteName + ".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public void sendRefresh(String[][] sprites) {
        System.out.println("sendRefresh");
        if(sprites == null) {
            System.out.println("is null");
        }
        this.levelSprites = sprites;
        this.repaint();
    }
}
