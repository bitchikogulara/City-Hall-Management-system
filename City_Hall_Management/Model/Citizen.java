package Model;

import java.io.*;
import java.util.*;

public class Citizen {
    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }

    public Death getDeath() {
        return death;
    }

    public void setDeath(Death death) {
        this.death = death;
    }

    public CityHall getCityHall() {
        return cityHall;
    }

    public void setCityHall(CityHall cityHall) {
        this.cityHall = cityHall;
    }

    private int idNumber;
    private String fName;
    private String lName;
    private Birth birth;
    private Death death;
    private CityHall cityHall;

    // Constructor without death (still alive)
    public Citizen(int idNumber, String fName, String lName, Birth birth, CityHall cityHall) {
        this.idNumber = idNumber;
        this.fName = fName;
        this.lName = lName;
        this.birth = birth;
        this.cityHall = cityHall;
        this.death = null;
    }

    // Constructor with death (deceased)
    public Citizen(int idNumber, String fName, String lName, Birth birth, CityHall cityHall, Death death) {
        this.idNumber = idNumber;
        this.fName = fName;
        this.lName = lName;
        this.birth = birth;
        this.cityHall = cityHall;
        this.death = death;
    }

    public String getFullName() {
        return fName + " " + lName;
    }
}
