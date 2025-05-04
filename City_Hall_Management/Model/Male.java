package Model;

import java.io.*;
import java.util.*;

public class Male extends Citizen {
    private List<Marriage> marriages;
    private List<Citizen> children;

    public Male(int idNumber, String fName, String lName, Birth birth, CityHall cityHall) {
        super(idNumber, fName, lName, birth, cityHall);
        this.marriages = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public Male(int idNumber, String fName, String lName, Birth birth, CityHall cityHall, Death death) {
        super(idNumber, fName, lName, birth, cityHall, death);
        this.marriages = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public List<Marriage> getMarriages() {
        return marriages;
    }

    public List<Citizen> getChildren() {
        return children;
    }

    public void addMarriage(Marriage m) {
        marriages.add(m);
    }

    public void addChild(Citizen child) {
        children.add(child);
    }
}
