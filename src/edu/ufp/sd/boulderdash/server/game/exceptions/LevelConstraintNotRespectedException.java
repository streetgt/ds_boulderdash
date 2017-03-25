package edu.ufp.sd.boulderdash.server.game.exceptions;

/**
 * LevelConstraintNotRespectedException
 *
 * Raises an 'LevelConstraintNotRespectedException' exception. Given the
 * exception message.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-24
 */
public class LevelConstraintNotRespectedException extends Exception {

    /**
     * Class constructor
     *
     * @param message Exception backtrace message
     */
    public LevelConstraintNotRespectedException(String message) {
        super(message);
    }
}
