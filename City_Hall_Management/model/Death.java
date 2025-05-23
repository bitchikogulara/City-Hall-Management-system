package model;

import java.io.Serializable;
import java.util.*;

public class Death implements Serializable{
    private static final long serialVersionUID = 1L;
    private int deathId;
    private Date date;
    private Citizen citizen;
    private CityHall cityHall;

    public Death(int deathId, Date date, Citizen citizen, CityHall cityHall) {
        this.deathId = deathId;
        this.date = date;
        this.citizen = citizen;
        this.cityHall = cityHall;
    }

    public int getDeathId() {
        return deathId;
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
}
