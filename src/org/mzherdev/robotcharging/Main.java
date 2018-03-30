package org.mzherdev.robotcharging;

import org.mzherdev.robotcharging.strategy.GentlemanStrategy;
import org.mzherdev.robotcharging.strategy.GreedyStrategy;
import org.mzherdev.robotcharging.strategy.RandomStrategy;
import org.mzherdev.robotcharging.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        if(args.length != 6)
            throw new IllegalArgumentException("Please provide strategies for 6 robots");

        // 2 permits -  plug and cable
        Semaphore semaphore = new Semaphore(2);

        List<Robot> robots = new ArrayList<>();
        for(int i = 0; i < args.length; i++) {
            int strategyNumber = parseStringToInt(args[i]);
            robots.add(new Robot(i + 1, semaphore, getStrategy(strategyNumber)));
        }

        // setting robot`s neighbours
        for(int i = 0; i < robots.size(); i++) {
            Robot robot = robots.get(i);
            robot.getNeighbours()[0] = robots.get(i == 0 ? robots.size() - 1 : i-1);
            robot.getNeighbours()[1] = robots.get(i == 5 ? 0 : i+1);
        }

        robots.forEach((Thread::start));

        if(shouldStopProgram(robots))
            System.exit(0);
    }

    private static int parseStringToInt(String arg) {
        int num = 0;
        try {
            num = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            System.out.println("Please provide correct strategy number");
        }
        return num;
    }

    private static Strategy getStrategy(int strategyNumber) {
        Strategy strategy;
        switch (strategyNumber) {
            case 1: strategy = new RandomStrategy(); break;
            case 2: strategy = new GreedyStrategy(); break;
            case 3: strategy = new GentlemanStrategy(); break;
            default: throw new IllegalArgumentException("Strategy number is incorrect.\n Choose: 1 for Random, 2 for Greedy, 3 for Gentleman");
        }
        return strategy;
    }

    private static boolean shouldStopProgram(List<Robot> robots) {
        boolean stopProgram = false;
        int robotsChargeSum = robots.stream().mapToInt(Robot::getCharge).sum();
        long fullChargedCount = robots.stream().filter(Robot::isFullCharged).count();
        long fullDischargedCount = robots.stream().filter(robot ->  !robot.isFullCharged()).count();

        if(robotsChargeSum == 0 || robotsChargeSum == robots.size() * 100) {
            stopProgram = true;
        } else if(fullChargedCount == 3 && fullDischargedCount == 3)
            stopProgram = true;
        return stopProgram;
    }
}
