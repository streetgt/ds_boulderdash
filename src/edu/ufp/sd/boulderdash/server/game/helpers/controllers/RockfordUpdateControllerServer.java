package edu.ufp.sd.boulderdash.server.game.helpers.controllers;

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
public class RockfordUpdateControllerServer implements Runnable {

    private LevelModelRoom levelModelServer;
    private Thread elementMovingThread;
    private int[] rockfordInstance = new int[2];
    private int[] rockfordPositionX = new int[2];
    private int[] rockfordPositionY = new int[2];
    private boolean[] rockfordHasMoved = new boolean[2];

    /**
     * Class constructor
     *
     * @param levelModelServer Level model
     */
    public RockfordUpdateControllerServer(LevelModelRoom levelModelServer) {
        this.levelModelServer = levelModelServer;
        this.elementMovingThread = new Thread(this);
        this.elementMovingThread.start();
        this.rockfordHasMoved[0] = false;
        this.rockfordHasMoved[1] = false;
    }

    /**
     * Watches for elements to be moved
     */
    public void run() {
        while (this.levelModelServer.isGameRunning()) {
            if (!this.levelModelServer.getGamePaused()) {
                for (int i = 0; i < 2; i++) {
                    if (this.rockfordHasMoved[i]) {
                        this.levelModelServer.setPositionOfRockford(rockfordInstance[i], rockfordPositionX[i], rockfordPositionY[i]);
                        this.rockfordHasMoved[i] = false;
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Moves Rockford
     *
     * @param rockfordPositionX Next horizontal position on the grid
     * @param rockfordPositionY Next vertical position on the grid
     */
    public void moveRockford(int index, int rockfordPositionX, int rockfordPositionY) {
        this.rockfordInstance[index] = index;
        this.rockfordPositionX[index] = rockfordPositionX;
        this.rockfordPositionY[index] = rockfordPositionY;
        this.rockfordHasMoved[index] = true;
    }
}
