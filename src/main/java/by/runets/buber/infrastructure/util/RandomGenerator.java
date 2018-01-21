package by.runets.buber.infrastructure.util;

import by.runets.buber.domain.entity.Point;
import by.runets.buber.domain.enumeration.TrafficEnum;
import by.runets.buber.infrastructure.constant.OrderConstant;

import java.util.Random;

public class RandomGenerator {


    public static Point generatePoint(){
        Random randomGenerator = new Random();
        Double latitude = (double) randomGenerator.nextInt(100);
        Double longitude = (double) randomGenerator.nextInt(100);
        return new Point(latitude, longitude);
    }

    public static Double generateAverageSpeed(TrafficEnum trafficEnum){
        Random randomGenerator = new Random();
        return (double) (trafficEnum == TrafficEnum.CITY
                ? randomGenerator.nextInt(OrderConstant.CITY_AVERAGE_SPEED)
                : randomGenerator.nextInt(OrderConstant.HIGHWAY_AVERAGE_SPEED - OrderConstant.CITY_AVERAGE_SPEED) + OrderConstant.CITY_AVERAGE_SPEED);
    }
}
