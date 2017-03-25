package edu.ufp.sd.boulderdash.server.game;

/**
 * MagicWallModel
 *
 * Represents the magic wall.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 */
public class MagicWallModel extends DisplayableElementModel {

    private static String spriteName;
    private static boolean isDestructible;
    private static boolean canMove;
    private static boolean impactExplosive;
    private static boolean animate;
    private static int priority;
    private static boolean falling;
    private static String collideSound;
    
    /**
     * Static dataset Specifies the physical parameters of the object
     */
    static {
        spriteName = "magicwall";
        isDestructible = false;
        canMove = false;
        impactExplosive = false;
        animate = false;
        priority = 3;
        falling = false;
        collideSound = "touch";
    }

    /**
     * Class constructor
     */
    public MagicWallModel() {
        super(isDestructible, canMove, spriteName, priority, impactExplosive, animate, falling, collideSound);
    }
}
