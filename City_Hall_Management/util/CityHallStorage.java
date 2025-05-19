package util;

import java.io.*;

import model.CityHall;

public class CityHallStorage {
    private static final String FILE_NAME = "cityhall.ser";

    public static void save(CityHall cityHall) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(cityHall);
        } catch (IOException e) {
            System.err.println("Failed to save CityHall data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static CityHall load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No existing data found, creating new CityHall.");
            return new CityHall("Évry");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof CityHall) {
                return (CityHall) obj;
            } else {
                System.err.println("Invalid data format. Creating new CityHall.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }

        return new CityHall("Évry");
    }
}
