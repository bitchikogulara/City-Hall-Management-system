package model;
import java.io.Serializable;
import java.util.*;

public class Birth implements Serializable {
    private static final long serialVersionUID = 1L;
    private int birthId;
    private Date date;
    private Citizen citizen;
    private CityHall cityHall;
    private Male father;     // nullable
    private Female mother;   // nullable

    public Birth(int birthId, Date date, Citizen citizen, CityHall cityHall, Male father, Female mother) {
        this.birthId = birthId;
        this.date = date;
        this.citizen = citizen;
        this.cityHall = cityHall;
        this.father = father;
        this.mother = mother;
    }

    public int getBirthId() {
        return birthId;
    }

    public Date getDate() {
        return date;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public CityHall getCityHall() {
        return cityHall;
    }

    public Male getFather() {
        return father;
    }

    public Female getMother() {
        return mother;
    }
}
