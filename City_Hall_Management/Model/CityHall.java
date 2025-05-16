package Model;

import java.util.*;

public class CityHall {
    private String name;

    private List<Citizen> citizens;
    private List<Marriage> marriages;
    private List<Divorce> divorces;
    private List<Birth> births;
    private List<Death> deaths;

    private final List<UpdateListener> listeners;

    public CityHall(String name) {
        this.name = name;
        this.citizens = new ArrayList<>();
        this.marriages = new ArrayList<>();
        this.divorces = new ArrayList<>();
        this.births = new ArrayList<>();
        this.deaths = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    // Listener management
    public void registerListener(UpdateListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(UpdateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (UpdateListener listener : listeners) {
            listener.onDataUpdated();
        }
    }

    // Citizen management
    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
        notifyListeners();
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
        notifyListeners();
    }

    public List<Birth> getBirths() {
        return births;
    }

    // Death management
    public void addDeath(Death death) {
        deaths.add(death);
        notifyListeners();
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    // Marriage management
    public void addMarriage(Marriage marriage) {
        marriages.add(marriage);
        notifyListeners();
    }

    public List<Marriage> getMarriages() {
        return marriages;
    }

    // Divorce management
    public void addDivorce(Divorce divorce) {
        divorces.add(divorce);
        notifyListeners();
    }

    public List<Divorce> getDivorces() {
        return divorces;
    }
}
