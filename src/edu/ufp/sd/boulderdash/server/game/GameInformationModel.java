package edu.ufp.sd.boulderdash.server.game;

import java.util.Observable;

/**
 * GameInformationModel will contain all the data which will go to the
 * InformationPanel.
 *
 * @author Colin Leverger <me@colinleverger.fr>
 * @since 2015-06-19
 *
 */
public class GameInformationModel extends Observable {

    private int[] score;
    private int remainingsDiamonds;
    private int timer;

    /**
     * GameInformationModel - Constructor
     *
     * @param remainingsDiamonds
     */
    public GameInformationModel(int remainingsDiamonds) {
        this.score = new int[2];
        for (int i = 0; i < score.length; i++) {
            this.score[i] = 0;
        }
        this.remainingsDiamonds = remainingsDiamonds;
        this.timer = 0;
    }

    /**
     * Gets the current score gived a index
     *
     * @param index
     * @return
     */
    public int getScore(int index) {
        return score[index];
    }

    /**
     * Gets the current score
     *
     * @return
     */
    public int[] getScore() {
        return score;
    }

    /**
     * Set score
     *
     * @param score
     */
    public void setScore(int[] score) {
        this.score = score;
    }

    /**
     * Returns the actual number of remaining diamonds
     *
     * @return Remaining diamonds
     */
    public int getRemainingsDiamonds() {
        return remainingsDiamonds;
    }

    /**
     * Sets the number of remainingDiamonds
     *
     * @param remainingDiamonds Remaining diamonds
     */
    public void setRemainingsDiamonds(int remainingDiamonds) {
        this.remainingsDiamonds = remainingDiamonds;
    }

    /**
     * Gets the timer
     *
     * @return Timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the timer
     *
     * @param timer Timer
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }

    /**
     * Increments the score & notify observers
     */
    public void incrementScore(int index) {
        this.score[index] += 1;
        this.myNotify();
    }

    /**
     * Generic function which will notify the observers.
     */
    private void myNotify() {
        this.notifyObservers();
        this.setChanged();
    }

    /**
     * Decrement of one the number total of remaining diamonds.
     */
    public void decrementRemainingsDiamonds() {
        if (remainingsDiamonds > 0) {
            this.remainingsDiamonds -= 1;
            this.myNotify();
        }
    }

    /**
     * Reset details about object
     */
    public void resetInformations() {
        for (int i = 0; i < this.score.length; i++) {
            this.score[i] = 0;
        }
        this.remainingsDiamonds = remainingsDiamonds;
        this.timer = 0;
    }

    /**
     * Gets the Rockford with more collected diamonds
     *
     * @return
     */
    public int getRockfordMoreDiamonds() {
        return score[1] > score[0] ? 1 : 0;
    }
}
