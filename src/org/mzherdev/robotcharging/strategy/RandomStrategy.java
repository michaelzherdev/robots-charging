package org.mzherdev.robotcharging.strategy;

import org.mzherdev.robotcharging.PrintUtil;
import org.mzherdev.robotcharging.Robot;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

public class RandomStrategy implements Strategy{

    static ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public void charge(Robot robot) {
        Semaphore semaphore = robot.getSemaphore();
        try {
            semaphore.acquire();
            while (robot.getCharge() < 100) {
                robot.setCharge(robot.getCharge() + 10);
                PrintUtil.printCharging(robot);
                sleep(500 + random.nextInt(100, 300));
            }
            robot.setFullCharged(true);
            PrintUtil.printCharged(robot);
            semaphore.release();

            robot.discharge();
            robot.checkChargeIsNotEmpty();

        } catch(InterruptedException e) {
            System.out.println (e.getMessage());
        }
    }
}
