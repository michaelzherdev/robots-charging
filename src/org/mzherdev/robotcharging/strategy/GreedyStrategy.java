package org.mzherdev.robotcharging.strategy;

import org.mzherdev.robotcharging.PrintUtil;
import org.mzherdev.robotcharging.Robot;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class GreedyStrategy implements Strategy {

    private volatile boolean shouldCharge = false;

    @Override
    public void charge(Robot robot) {
        Semaphore semaphore = robot.getSemaphore();
        long robotId = robot.getId();
        try {
            if(robot.getCharge() < 60) {
                semaphore.acquire();
                while (robot.getCharge() < 100) {
                    robot.setCharge(robot.getCharge() + 10);
                    PrintUtil.printCharging(robot);
                    sleep(500);
                }
                robot.setFullCharged(true);
                PrintUtil.printCharged(robot);
                semaphore.release();
            } else {
                robot.discharge();
            }

            robot.checkChargeIsNotEmpty();

        } catch(InterruptedException e) {
            System.out.println (e.getMessage());
        }
    }
}
