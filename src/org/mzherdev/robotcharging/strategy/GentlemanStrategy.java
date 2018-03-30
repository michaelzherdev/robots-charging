package org.mzherdev.robotcharging.strategy;

import org.mzherdev.robotcharging.PrintUtil;
import org.mzherdev.robotcharging.Robot;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;


public class GentlemanStrategy implements Strategy {

    @Override
    public void charge(Robot robot) {
        Semaphore semaphore = robot.getSemaphore();
        try {
            Optional<Robot> robotWithSmallerCharge = Arrays.stream(robot.getNeighbours())
                    .filter(neighbour -> neighbour.getCharge() < robot.getCharge()).findAny();
            if(robotWithSmallerCharge.isPresent()) {
                sleep(200);
            }

            semaphore.acquire();
            while (robot.getCharge() < 100) {
                robot.setCharge(robot.getCharge() + 10);
                PrintUtil.printCharging(robot);
                sleep(500);
            }

            robot.setFullCharged(true);
            PrintUtil.printCharged(robot);
            semaphore.release();

            robot.discharge();
            robot.checkChargeIsNotEmpty();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
