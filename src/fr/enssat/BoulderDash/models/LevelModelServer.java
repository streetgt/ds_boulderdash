package fr.enssat.BoulderDash.models;

import edu.ufp.sd.boulderdash.client.BoulderDashClientRI;
import fr.enssat.BoulderDash.exceptions.LevelConstraintNotRespectedException;
import fr.enssat.BoulderDash.exceptions.UnknownModelException;
import fr.enssat.BoulderDash.helpers.LevelLoadHelper;
import fr.enssat.BoulderDash.helpers.AudioLoadHelper;
import fr.enssat.BoulderDash.helpers.ModelConvertHelper;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;

/**
 * LevelModel
 *
 * Levels are loaded from XML file. The view knows the model, the controller is
 * going to modify the model in function of the game panel. The model notifies
 * the view when there are changes on it.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class LevelModelServer extends Observable implements Runnable {

    private ArrayList<BoulderDashClientRI> clients = new ArrayList<>(2);
    private String roomName;
    private DisplayableElementModel[][] groundGrid;
    private String levelName;
    private int sizeWidth = 0;
    private int sizeHeight = 0;
    private LevelLoadHelper levelLoadHelper;
    private ArrayList<RockfordModel> rockfords = new ArrayList<>(2);
    private GameInformationModel gameInformationModel;
    private boolean gameRunning;
    private boolean gamePaused;
    private boolean gameHasEnded;

    /**
     * Sprite animation thread
     */
    private Thread spriteAnimator;

    /**
     * Animation speed
     */
    private final int DELAY = 25;

    /**
     * Class constructor
     *
     * @param levelName Level name
     * @param audioLoadHelper Audio load helper
     */
    public LevelModelServer(String levelName) {
        this.levelName = levelName;
        this.gamePaused = false;
        this.gameRunning = true;
        this.gameHasEnded = false;

        this.levelLoadHelper = new LevelLoadHelper(this.levelName);

        this.groundGrid = this.levelLoadHelper.getGroundGrid();
        this.sizeWidth = this.levelLoadHelper.getWidthSizeValue();
        this.sizeHeight = this.levelLoadHelper.getHeightSizeValue();
        
        this.gameInformationModel = new GameInformationModel(this.levelLoadHelper.getDiamondsToCatch());

        this.createLimits();

        this.initRockford();
        this.initThreadAnimator();
        System.out.println("LevelModelServer() - constructor()");
    }

    /**
     * Initializes the animator thread
     */
    private void initThreadAnimator() {
        this.spriteAnimator = new Thread(this);
        this.spriteAnimator.start();
    }

    /**
     * Initializes the Rockfords position attributes
     */
    private void initRockford() {
        this.rockfords = this.levelLoadHelper.getRockfordIntances();
    }

    /**
     * Creates the limits Puts steel walls all around the game panel
     */
    private void createLimits() {
        int maxWidth = this.sizeWidth - 1;
        int maxHeight = this.sizeHeight - 1;

        for (int x = 0; x < this.sizeWidth; x++) {
            this.groundGrid[x][0] = new SteelWallModel();
            this.groundGrid[x][maxHeight] = new SteelWallModel();
        }
        for (int y = 0; y < this.sizeHeight; y++) {
            this.groundGrid[0][y] = new SteelWallModel();
            this.groundGrid[maxWidth][y] = new SteelWallModel();
        }
    }

    public void resetLevelModel() {
        this.groundGrid = this.levelLoadHelper.getGroundGrid();
        this.gameRunning = true;
        this.gameInformationModel.resetInformations();
    }

    /**
     * Updates the horizontal & vertical positions of a Rockford in the model
     *
     * @param posX Horizontal position of Rockford
     * @param posY Vertical position of Rockford
     */
    public void updateRockfordPosition(int index, int posX, int posY) {
        this.rockfords.get(index).setPositionX(posX);
        this.rockfords.get(index).setPositionY(posY);
    }

    /**
     * Checks whether position is out-of-bounds or not
     *
     * @param posX Horizontal position
     * @param posY Vertical position
     */
    private boolean isOutOfBounds(int posX, int posY) {
        if (posX > 0 && posY > 0 && posX < this.getLevelLoadHelper().getHeightSizeValue() && posY < this.getLevelLoadHelper().getWidthSizeValue()) {
            return false;
        }

        return true;
    }

    /**
     * Plays collision sound
     */
    private void playCollisionSound(int index, int posX, int posY) {
        String collisionSound = null;

        if (this.getRockford(index).isCollisionDone() == false) {
            // Out of bounds?
            if (this.isOutOfBounds(posX, posY) == true) {
                collisionSound = "touch";
            } else {
                DisplayableElementModel nextElement = this.groundGrid[posX][posY];
                collisionSound = nextElement.getCollideSound();
            }

            this.getRockford(index).setCollisionDone(true);
        }

        if (collisionSound != null) {
            // Play sound to player
            //this.audioLoadHelper.playSound(collisionSound);
        }
    }

    /**
     * Gets the horizontal position of Rockford from the model
     *
     * @return Horizontal position of Rockford
     */
    public int getRockfordPositionX(int index) {
        return this.rockfords.get(index).getPositionX();
    }

    /**
     * Sets the new Rockford position
     *
     * @param posX Next horizontal position on the grid
     * @param posY Next vertical position on the grid
     */
    public void setPositionOfRockford(int index, int posX, int posY) {
        int oldX = this.getRockfordPositionX(index);
        int oldY = this.getRockfordPositionY(index);

        if (this.groundGrid[posX][posY].getSpriteName().compareTo("diamond") == 0) {
            this.gameInformationModel.incrementScore(index);
            this.gameInformationModel.decrementRemainingsDiamonds();

            if (this.gameInformationModel.getRemainingsDiamonds() == 0) {
                System.out.println("All diamonds found!");
                this.gameRunning = false;
                this.localNotifyObservers();
            }
        }

        this.playCollisionSound(index, posX, posY);

        // Check that we are not out of bound...
        if (this.isOutOfBounds(posX, posY) == false) {
            // Create a new empty model in the old pos of Rockford
            this.groundGrid[oldX][oldY] = new EmptyModel();

            // Save the x / y pos of Rockford in the levelModel only
            this.updateRockfordPosition(index, posX, posY);

            this.groundGrid[posX][posY] = this.getRockford(index);
        }
    }    

    /**
     * Gets the vertical position of Rockford from the model
     *
     * @param rockford instances index
     *
     * @return Vertical position of Rockford
     */
    public int getRockfordPositionY(int index) {
        return this.rockfords.get(index).getPositionY();
    }

    /**
     * Gets the Rockford object instance
     *
     * @return Rockford object
     */
    public RockfordModel getRockford(int index) {
        return this.rockfords.get(index);
    }

    public ArrayList<RockfordModel> getRockfords() {
        return rockfords;
    }

    public void setRockfords(ArrayList<RockfordModel> rockfords) {
        this.rockfords = rockfords;
    }

    /**
     * Gets the displayable element at given positions
     *
     * @param x Block horizontal position
     * @param y Block vertical position
     * @return Displayable element at given positions
     */
    public DisplayableElementModel getDisplayableElement(int x, int y) {
        return this.groundGrid[x][y];
    }

    /**
     * Gets the image at given positions
     *
     * @param x Block horizontal position
     * @param y Block vertical position
     * @return Image at given positions
     */
    public BufferedImage getImage(int x, int y) {
        DisplayableElementModel elementModel = this.getDisplayableElement(x, y);

        if (elementModel == null) {
            return new DirtModel().getSprite();
        }

        return elementModel.getSprite();
    }

    /**
     * Return whether rockford is in model or not Notice: not optimized, be
     * careful
     *
     * @return Whether rockford is in model or not
     */
    public boolean areRockfordsInModel() {
        boolean areInModel = false;

        // Iterate and catch it!
        for (int x = 0; x < this.getSizeWidth() && !areInModel; x++) {
            for (int y = 0; y < this.getSizeHeight() && !areInModel; y++) {
                if (this.groundGrid[x][y] != null && this.groundGrid[x][y].getSpriteName().compareTo("rockford") == 0 && this.groundGrid[x][y].getSpriteName().compareTo("rockford2") == 0) {
                    areInModel = true;
                }
            }
        }

        return areInModel;
    }

    /**
     * Returns number of diamonds
     *
     * @return Number of diamonds
     */
    public int countDiamonds() {
        int numberOfDiamonds = 0;

        // Iterate and catch it!
        for (int x = 0; x < this.getSizeWidth(); x++) {
            for (int y = 0; y < this.getSizeHeight(); y++) {
                if (this.groundGrid[x][y] != null && this.groundGrid[x][y].getSpriteName().compareTo("diamond") == 0) {
                    numberOfDiamonds += 1;
                }
            }
        }

        return numberOfDiamonds;
    }

    /**
     * Returns whether constraints on model are respected or not
     */
    public void checkConstraints() throws LevelConstraintNotRespectedException {
        // Diamonds number?
        if (this.countDiamonds() < 3) {
            throw new LevelConstraintNotRespectedException("Add at least 3 diamonds!");
        }

        // Rockfords in the model?
        if (!this.areRockfordsInModel()) {
            throw new LevelConstraintNotRespectedException("Add Rockfords on the map!");
        }
    }

    /**
     * Gets the level horizontal size
     *
     * @return Horizontal size
     */
    public int getSizeWidth() {
        return this.sizeWidth;
    }

    /**
     * Sets the level horizontal size
     *
     * @param sizeWidth Horizontal size
     */
    public void setSizeWidth(int sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    /**
     * Gets the level vertical size
     *
     * @return Vertical size
     */
    public int getSizeHeight() {
        return this.sizeHeight;
    }

    /**
     * Sets the level vertical size
     *
     * @return sizeHeight Vertical size
     */
    public void setSizeHeight(int sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    /**
     * Gets the ground level model
     *
     * @return Ground level model
     */
    public DisplayableElementModel[][] getGroundLevelModel() {
        return groundGrid;
    }

    /**
     * Notify observers about a model change
     */
    private void localNotifyObservers() {
        this.notifyObservers();
        this.setChanged();
    }

    /**
     * Update the current sprite Notifies the observers
     *
     * @param x Sprite block horizontal position
     * @param y Sprite block vertical position
     */
    public void updateSprites(int x, int y) {
        if (groundGrid[x][y] == null) {
            groundGrid[x][y] = new DirtModel();
        }

        groundGrid[x][y].update(System.currentTimeMillis());
        this.localNotifyObservers();
    }

    /**
     * Update all the sprites So that they can be animated
     */
    @Override
    public void run() {
        System.out.println("LevelModelServer() - ThreadID: " + Thread.currentThread().getId());
        while (gameRunning) {
            if (!gamePaused) {
                for (int x = 0; x < this.getSizeWidth(); x++) {
                    for (int y = 0; y < this.getSizeHeight(); y++) {
                        this.updateSprites(x, y);
                    }
                }
            }

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * Increments the user score
     */
    public void incrementScore(int index) {
        this.gameInformationModel.incrementScore(index);
    }

    /**
     * Gets the associated level load helper
     *
     * @return Level load helper
     */
    public LevelLoadHelper getLevelLoadHelper() {
        return this.levelLoadHelper;
    }

    /**
     * sets the game to a defined state
     *
     * @param gameRunning Whether game is running or not
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        // Timer to refresh the view properly...
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.localNotifyObservers();
    }

    /**
     * tells if the game is running
     *
     * @return whether the game is running or not
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * When a boulder is falling on Rockford there is an explosion around him
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void exploseGround(int index, int x, int y) {
        this.groundGrid[x][y] = new EmptyModel();
        this.groundGrid[x + 1][y] = new EmptyModel();
        this.groundGrid[x - 1][y] = new EmptyModel();
        this.groundGrid[x][y + 1] = new EmptyModel();
        this.groundGrid[x + 1][y + 1] = new EmptyModel();
        this.groundGrid[x - 1][y + 1] = new EmptyModel();
        this.groundGrid[x][y - 1] = new EmptyModel();
        this.groundGrid[x + 1][y - 1] = new EmptyModel();
        this.groundGrid[x - 1][y - 1] = new EmptyModel();
        this.getRockford(index).setHasExplosed(true);

        // Again a sleep to notify the observers properly
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.localNotifyObservers();
    }

    /**
     * Makes the DisplayableElement[x][y] fall one box down
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisDisplayableElementFall(int x, int y) {
        this.groundGrid[x][y].setFalling(true);
        this.groundGrid[x][y + 1] = this.groundGrid[x][y];
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] slide left
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisBoulderSlideLeft(int x, int y) {
        this.groundGrid[x][y].setFalling(true);
        this.groundGrid[x - 1][y + 1] = this.groundGrid[x][y];
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] slide right
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void makeThisBoulderSlideRight(int x, int y) {
        this.groundGrid[x][y].setFalling(true);
        this.groundGrid[x + 1][y + 1] = this.groundGrid[x][y];
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] transform into a diamond
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void transformThisBoulderIntoADiamond(int x, int y) {
        this.groundGrid[x][y + 2] = new DiamondModel();
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] moving to right
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void moveThisBoulderToRight(int x, int y) {
        this.groundGrid[x + 1][y] = this.groundGrid[x][y];
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Makes the BoulderModel[x][y] moving to left
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void moveThisBoulderToLeft(int x, int y) {
        this.groundGrid[x - 1][y] = this.groundGrid[x][y];
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Deletes the BoulderModel[x][y]
     *
     * @param x Object horizontal position
     * @param y Object vertical position
     */
    public void deleteThisBoulder(int x, int y) {
        this.groundGrid[x][y] = new EmptyModel();
    }

    /**
     * Gets gameInformationModel
     *
     * @return gameInfos like score, remainings Diamonds etc
     */
    public GameInformationModel getGameInformationModel() {
        return this.gameInformationModel;
    }

    /**
     * Explose the brick wall
     *
     * @param x
     * @param y
     */
    public void exploseThisBrickWall(int x, int y) {
        this.groundGrid[x][y] = new EmptyModel();
        this.groundGrid[x][y + 1] = new EmptyModel();
    }

    /**
     * Expand the ExpandingWallModel to left
     *
     * @param x
     * @param y
     */
    public void expandThisWallToLeft(int x, int y) {
        this.groundGrid[x - 1][y] = new ExpandingWallModel();
    }

    /**
     * Expand the ExpandingWallModel to right
     *
     * @param x
     * @param y
     */
    public void expandThisWallToRight(int x, int y) {
        this.groundGrid[x + 1][y] = new ExpandingWallModel();
    }

    /**
     * Set the gamePaused variable
     *
     * @param gamePaused
     */
    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    /**
     * Get the gamePaused variable
     *
     * @return gamePaused
     */
    public boolean getGamePaused() {
        return this.gamePaused;
    }

    public boolean isGameHasEnded() {
        return gameHasEnded;
    }

    public void setGameHasEnded(boolean gameHasEnded) {
        this.gameHasEnded = gameHasEnded;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ArrayList<BoulderDashClientRI> getClients() {
        return clients;
    }

    public void setClients(ArrayList<BoulderDashClientRI> clients) {
        this.clients = clients;
    }
}
