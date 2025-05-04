package Model;

import java.io.*;
import java.util.*;


public class CityHall {
    private String name;

    private List<Citizen> citizens;
    private List<Marriage> marriages;
    private List<Divorce> divorces;
    private List<Birth> births;
    private List<Death> deaths;

    public CityHall(String name) {
        this.name = name;
        this.citizens = new ArrayList<>();
        this.marriages = new ArrayList<>();
        this.divorces = new ArrayList<>();
        this.births = new ArrayList<>();
        this.deaths = new ArrayList<>();
    }

    // Citizen management
    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
    }

    public Citizen findCitizenById(int id) {
        for (Citizen c : citizens) {
            if (c.getIdNumber() == id) return c;
        }
        return null;
    }

    public List<Citizen> getCitizens() {
        return citizens;
    }

    // Birth management
    public void addBirth(Birth birth) {
        births.add(birth);
    }

    public List<Birth> getBirths() {
        return births;
    }

    // Death management
    public void addDeath(Death death) {
        deaths.add(death);
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    // Marriage management
    public void addMarriage(Marriage marriage) {
        marriages.add(marriage);
    }

    public List<Marriage> getMarriages() {
        return marriages;
    }

    // Divorce management
    public void addDivorce(Divorce divorce) {
        divorces.add(divorce);
    }

    public List<Divorce> getDivorces() {
        return divorces;
    }
}
