package edu.ufp.sd.boulderdash.server.game.helpers.controllers;

import edu.ufp.sd.boulderdash.server.game.DirtModel;
import edu.ufp.sd.boulderdash.server.game.DisplayableElementModel;
import edu.ufp.sd.boulderdash.server.game.LevelModelRoom;

/**
 * ElementPositionUpdateHelper
 *
 * Updates position of all elements displayed on the map, according to their
 * next potential position. Each object has a weight, which is used to compare
 * their power to destroy in the food chain. Sorry for that Darwinism.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class BoulderAndDiamondControllerServer implements Runnable {

    private LevelModelRoom levelModelServer;
    private Thread elementMovingThread;

    /**
     * Class constructor
     *
     * @param levelModelServer Level model
     */
    public BoulderAndDiamondControllerServer(LevelModelRoom levelModelServer) {
        this.levelModelServer = levelModelServer;

        // Start thread
        this.elementMovingThread = new Thread(this);
        this.elementMovingThread.start();
    }

    /**
     * Watches for elements to be moved
     */
    public void run() {
        while (this.levelModelServer.isGameRunning()) {
            if (!this.levelModelServer.getGamePaused()) {
                this.manageFallingObject();
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Scan the ground to detect the boulders & the diamonds, then make them
     * fall if necessary Note: scan of the ground upside down: we want things to
     * fall slowly !
     */
    private void manageFallingObject() {
        for (int x = this.levelModelServer.getSizeWidth() - 1; x >= 0; x--) {
            for (int y = this.levelModelServer.getSizeHeight() - 1; y >= 0; y--) {
                // Gets the spriteName of actual DisplayableElementModel object scanned
                DisplayableElementModel elementModel = this.levelModelServer.getGroundLevelModel()[x][y];

                if (elementModel == null) {
                    elementModel = new DirtModel();
                }

                String spriteName = elementModel.getSpriteName();

                // If it is a boulder or a diamond...
                if (spriteName.compareTo("boulder") == 0 || spriteName.compareTo("diamond") == 0) {
                    this.manageFall(x, y);
                } else if (spriteName.compareTo("expandingwall") == 0) {
                    if (this.expandWall(x, y).equals("left")) {
                        x -= 1;
                    }
                }
            }
        }
    }

    /**
     * Expand the wall at left & right
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    private String expandWall(int x, int y) {
        DisplayableElementModel elementLeft = this.levelModelServer.getGroundLevelModel()[x - 1][y];
        DisplayableElementModel elementRight = this.levelModelServer.getGroundLevelModel()[x + 1][y];
        String spriteNameLeft = elementLeft.getSpriteName();
        String spriteNameRight = elementRight.getSpriteName();

        String way = "";
        if (spriteNameLeft.compareTo("black") == 0) {
            this.levelModelServer.expandThisWallToLeft(x, y);
            way = "left";
        }
        if (spriteNameRight .compareTo("black") == 0) {
            this.levelModelServer.expandThisWallToRight(x, y);
            way = "right";
        }
        return way;
    }

    /**
     * Manages the fall of elements
     *
     * @param x Horizontal position
     * @param y Vertical position
     */
    private void manageFall(int x, int y) {
        // Get informed about Rockford surroundings
        DisplayableElementModel elementBelow = this.levelModelServer.getGroundLevelModel()[x][y + 1];
        DisplayableElementModel elementLeft = this.levelModelServer.getGroundLevelModel()[x - 1][y];
        DisplayableElementModel elementRight = this.levelModelServer.getGroundLevelModel()[x + 1][y];

        String spriteNameBelow = elementBelow.getSpriteName();
        String spriteNameLeft = elementLeft.getSpriteName();
        String spriteNameRight = elementRight.getSpriteName();

        // Then, process in case of the surrounding
        if (spriteNameBelow.compareTo("black") == 0) {
            this.levelModelServer.makeThisDisplayableElementFall(x, y);
        } else if (spriteNameBelow.compareTo("boulder") == 0) {
            // Boulders have to roll if they hit another boulder
            if (this.levelModelServer.getGroundLevelModel()[x - 1][y + 1].getSpriteName().compareTo("black") == 0) {
                this.levelModelServer.makeThisBoulderSlideLeft(x, y);
            } else if (this.levelModelServer.getGroundLevelModel()[x + 1][y + 1].getSpriteName().compareTo("black") == 0) {
                this.levelModelServer.makeThisBoulderSlideRight(x, y);
            }
        } else if (spriteNameBelow.compareTo("rockford") == 0 || spriteNameBelow.compareTo("rockford2") == 0 && this.levelModelServer.getGroundLevelModel()[x][y].isFalling()) {
            if (spriteNameBelow.compareTo("rockford") == 0) {
                System.out.println("exploseGround - rockford");
                this.levelModelServer.exploseGround(0, x, y + 1);
            } else {
                System.out.println("exploseGround - rockford2");
                this.levelModelServer.exploseGround(1, x, y + 1);
            }

            // TODO: mandar som aos 2 que morreram.
            //this.audioLoadHelper.playSound("die");

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.levelModelServer.setGameRunning(false);
        } else if (spriteNameBelow.compareTo("magicwall") == 0) {
            if (this.levelModelServer.getGroundLevelModel()[x][y].getSpriteName().compareTo("boulder") == 0
                    && (this.levelModelServer.getGroundLevelModel()[x][y + 2].getSpriteName().compareTo("dirt") == 0)
                    || this.levelModelServer.getGroundLevelModel()[x][y + 2].getSpriteName().compareTo("black") == 0) {
                if (this.levelModelServer.getGroundLevelModel()[x][y].isConvertible()) {
                    this.levelModelServer.transformThisBoulderIntoADiamond(x, y);
                } else {
                    this.levelModelServer.deleteThisBoulder(x, y);
                }
            }
        } else if (elementBelow.isDestructible() && !"dirt".equals(spriteNameBelow) && this.levelModelServer.getGroundLevelModel()[x][y].isFalling()) {
            this.levelModelServer.exploseThisBrickWall(x, y);
        } else if (spriteNameLeft.compareTo("rockford") == 0 && this.levelModelServer.getRockford(0).isRunningRight() && this.levelModelServer.getGroundLevelModel()[x + 1][y].getSpriteName().compareTo("black") == 0) {
            this.levelModelServer.moveThisBoulderToRight(x, y);
        } else if (spriteNameRight.compareTo("rockford") == 0 && this.levelModelServer.getRockford(0).isRunningLeft() && this.levelModelServer.getGroundLevelModel()[x - 1][y].getSpriteName().compareTo("black") == 0) {
            this.levelModelServer.moveThisBoulderToLeft(x, y);
        } else if (spriteNameLeft.compareTo("rockford2") == 0 && this.levelModelServer.getRockford(1).isRunningRight() && this.levelModelServer.getGroundLevelModel()[x + 1][y].getSpriteName().compareTo("black") == 0) {
            this.levelModelServer.moveThisBoulderToRight(x, y);
        } else if (spriteNameRight.compareTo("rockford2") == 0 && this.levelModelServer.getRockford(1).isRunningLeft() && this.levelModelServer.getGroundLevelModel()[x - 1][y].getSpriteName().compareTo("black") == 0) {
            this.levelModelServer.moveThisBoulderToLeft(x, y);
        } else {
            this.levelModelServer.getGroundLevelModel()[x][y].setFalling(false);
        }
    }
}
