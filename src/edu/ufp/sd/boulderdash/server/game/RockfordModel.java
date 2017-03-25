package edu.ufp.sd.boulderdash.server.game;

/**
 * RockfordModel
 *
 * Represents the hero of the game.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class RockfordModel extends DisplayableElementModel {

    private final String spriteName;
    private static boolean isDestructible;
    private static boolean canMove;
    private static boolean impactExplosive;
    private static boolean animate;
    private static int priority;
    private static boolean falling;
    private static String collideSound;

    /**
     * Maps possible states for Rockford
     */
    private boolean isCollisionDone = false;
    private boolean isStaying = true;
    private boolean isRunningLeft = false;
    private boolean isRunningRight = false;
    private boolean isRunningUp = false;
    private boolean isRunningDown = false;

    private boolean hasExploded;
    private int positionX;
    private int positionY;

    /**
     * Static dataset Specifies the physical parameters of the object
     */
    static {
        isDestructible = true;
        canMove = true;
        impactExplosive = true;
        animate = true;
        priority = 1;
        falling = false;
        collideSound = null;
    }

    /**
     * Class constructor
     */
    public RockfordModel(String spriteName) {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
        System.out.println("RockfordModel Contructor: " + spriteName);
        this.spriteName = spriteName;
        // Init the sprites in arrays
        this.hasExploded = false;

    }

    /**
     * Stops the Rockford movement
     */
    public void startStaying() {
        isCollisionDone = false;
        isStaying = true;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = false;
    }

    /**
     * Starts moving Rockford to the left
     */
    public void startRunningLeft() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = true;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = false;
    }

    /**
     * Starts moving Rockford to the right
     */
    public void startRunningRight() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = true;
        isRunningUp = false;
        isRunningDown = false;
    }

    /**
     * Rockford running up
     */
    public void startRunningUp() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = true;
        isRunningDown = false;
    }

    /**
     * Rockford running down
     */
    public void startRunningDown() {
        isCollisionDone = false;
        isStaying = false;
        isRunningLeft = false;
        isRunningRight = false;
        isRunningUp = false;
        isRunningDown = true;
    }

    /**
     * Gets whether Rockford collision has been handled or not
     *
     * @return Rockford collision handled or not
     */
    public boolean isCollisionDone() {
        return this.isCollisionDone;
    }

    /**
     * Sets whether Rockford collision has been handled or not
     *
     * @param isCollisionDone Rockford collision handled or not
     */
    public void setCollisionDone(boolean isCollisionDone) {
        this.isCollisionDone = isCollisionDone;
    }

    /**
     * Gets whether Rockford is standing still or not
     *
     * @return Rockford staying or not
     */
    public boolean isStaying() {
        return this.isStaying;
    }

    /**
     * Gets whether Rockford is running to the left or not
     *
     * @return Rockford running to the left or not
     */
    public boolean isRunningLeft() {
        return this.isRunningLeft;
    }

    /**
     * Gets whether Rockford is running to the right or not
     *
     * @return Rockford running to the right or not
     */
    public boolean isRunningRight() {
        return this.isRunningRight;
    }

    /**
     * Gets whether Rockford is running up or not
     *
     * @return Rockford running up, or not
     */
    public boolean isRunningUp() {
        return this.isRunningUp;
    }

    /**
     * Gets whether Rockford is running down or not
     *
     * @return Rockford running down, or not
     */
    public boolean isRunningDown() {
        return this.isRunningDown;
    }

    /**
     * Gets whether Rockford is running up or down, or not
     *
     * @return Rockford running up or down, or not
     */
    public boolean isRunningUpOrDown() {
        return this.isRunningUp() || this.isRunningDown();
    }

    /**
     * Return true if rockford has exploded (you = lose)
     *
     * @return Whether Rockford has exploded or not
     */
    public boolean getHasExplosed() {
        return hasExploded;
    }

    /**
     * Set rockford exploded state
     *
     * @param hasExploded Whether Rockford has exploded or not
     */
    public void setHasExplosed(boolean hasExploded) {
        this.hasExploded = hasExploded;
    }

    /**
     * Get rockford position X
     *
     * @return positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Set rockford position X
     *
     * @param positionX
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Get rockford position Y
     *
     * @return positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Set rockford position Y
     *
     * @param positionX
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

}
