package fr.enssat.BoulderDash.models;

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

    private int[] score = new int[2];
    private int remainingsDiamonds;
    private int timer;

    public GameInformationModel(int remainingsDiamonds) {
        this.score[0] = 0;
        this.score[1] = 0;
        this.remainingsDiamonds = remainingsDiamonds;
        this.timer = 0;
    }

    public int getScore(int index) {
        return score[index];
    }
    
    public int[] getScore() {
        return score;
    }
    
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
        this.score[0] = 0;
        this.score[0] = 1;
        this.remainingsDiamonds = remainingsDiamonds;
        this.timer = 0;
    }

    public int getRockfordMoreDiamonds()
    {
        if(score[1] > score[0]) 
            return 1;
        
        return 0;
    }
}
