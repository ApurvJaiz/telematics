package com.example.telematics;

import com.example.telematics.model.TelematicsData;

import java.util.*;

public class TestDataProvider {

    private static final int MAX_LATITUDE = 90;
    private static final int MAX_LONGITUDE = 180;
    private static final int MAX_FUEL_PERCENT = 100;
    private static final int MAX_SPEED = 100;

    public static List<TelematicsData> createBulkTelematicsData(int numberOfRecords) {
        List<String> vehicleIds = generateRandomVehicleNumbers(numberOfRecords/10);
        List<TelematicsData> testDataList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfRecords; i++) {
            TelematicsData telematicsData = new TelematicsData();
            telematicsData.setVehicleId(vehicleIds.get(random.nextInt(vehicleIds.size())));
            telematicsData.setTimestamp(new Date(System.currentTimeMillis()));
            telematicsData.setLatitude(random.nextDouble() * MAX_LATITUDE);
            telematicsData.setLongitude(random.nextDouble() * MAX_LONGITUDE);
            telematicsData.setFuelPercent(random.nextDouble() * MAX_FUEL_PERCENT);
            telematicsData.setSpeed(random.nextDouble() * MAX_SPEED);

            testDataList.add(telematicsData);
        }

        return testDataList;
    }


    public static List<String> generateRandomVehicleNumbers(int numberOfNumbers) {
        String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int MAX_DIGIT = 9;
        Random random = new Random();
        List<String> vehicleNumbers = new ArrayList<>();
        while (vehicleNumbers.size() < numberOfNumbers) {
            StringBuilder vehicleNumber = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                vehicleNumber.append(LETTERS[random.nextInt(LETTERS.length)]);
            }
            for (int i = 0; i < 3; i++) {
                vehicleNumber.append(random.nextInt(MAX_DIGIT + 1));
            }
            vehicleNumbers.add(vehicleNumber.toString());
        }
        return vehicleNumbers;
    }

    public TelematicsData createSampleTelematicsData() {
        TelematicsData telematicsData = new TelematicsData();
        telematicsData.setVehicleId("Vehicle123");
        telematicsData.setSpeed(80.0);
        telematicsData.setLatitude(44.968046);
        telematicsData.setLongitude(-94.420307);
        telematicsData.setFuelPercent(98.0);
        return telematicsData;
    }
}
