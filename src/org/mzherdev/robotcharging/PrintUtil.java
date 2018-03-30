package org.mzherdev.robotcharging;

public class PrintUtil {

    private PrintUtil() {}

    public static void printCharging(Robot robot) {
        System.out.println ("Робот " + robot.getId() + " заряжается... " + robot.getCharge() + "%");
    }

    public static void printCharged(Robot robot) {
        System.out.println("Робот " + robot.getId() + " зарядился на 100%");
    }

    public static void printDischarging(Robot robot) {
        System.out.println("Робот " + robot.getId() + " разряжается. Уровень заряда " + robot.getCharge());
    }

    public static void printTurnedOff(Robot robot) {
        System.out.println("Робот " + robot.getId() + " разрядился полностью");
    }
}
