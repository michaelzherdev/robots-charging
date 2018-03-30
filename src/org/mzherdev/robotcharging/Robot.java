package org.mzherdev.robotcharging;

import org.mzherdev.robotcharging.strategy.GreedyStrategy;
import org.mzherdev.robotcharging.strategy.Strategy;

import java.util.concurrent.Semaphore;

public class Robot extends Thread {

    private static final int START_CHARGE = 50;

    private final long id;
    private final Strategy strategy;

    private Robot[] neighbours = new Robot[2];
    private Semaphore semaphore;
    private int charge = START_CHARGE;
    private boolean isFullCharged = false;

    Robot(int id, Semaphore semaphore, Strategy strategy) {
        this.id=id;
        this.semaphore =semaphore;
        this.strategy = strategy;
    }

    @Override
    public long getId() {
        return id;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public Robot[] getNeighbours() {
        return neighbours;
    }

    public boolean isFullCharged() {
        return isFullCharged;
    }

    public void setFullCharged(boolean fullCharged) {
        isFullCharged = fullCharged;
    }

    public void run() {
        while (true)
            strategy.charge(this);
    }

    public void discharge() throws InterruptedException {
        if(strategy instanceof GreedyStrategy) {
            sleep(500);
            charge -= 5;
        } else {
            sleep(1000);
            charge -= 10;
        }
        isFullCharged = false;
        PrintUtil.printDischarging(this);
    }

    public void checkChargeIsNotEmpty() {
        if (charge == 0) {
            this.interrupt();
            PrintUtil.printTurnedOff(this);
        }
    }

}
