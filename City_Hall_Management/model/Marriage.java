package model;

import java.io.Serializable;
import java.util.*;

public class Marriage implements Serializable{
    private static final long serialVersionUID = 1L;
    private int marriageId;
    private Male groom;
    private Female bride;
    private Date date;
    private CityHall cityHall;
    private Divorce divorce; // null if not divorced

    public Marriage(int marriageId, Male groom, Female bride, Date date, CityHall cityHall) {
        this.marriageId = marriageId;
        this.groom = groom;
        this.bride = bride;
        this.date = date;
        this.cityHall = cityHall;
        this.divorce = null;
    }

    public int getMarriageId() {
        return marriageId;
    }

    public Male getGroom() {
        return groom;
    }

    public Female getBride() {
        return bride;
    }

    public Date getDate() {
        return date;
    }

    public CityHall getCityHall() {
        return cityHall;
    }

    public Divorce getDivorce() {
        return divorce;
    }

    public void setDivorce(Divorce divorce) {
        this.divorce = divorce;
    }

    public boolean isActive() {
        return divorce == null;
    }

    public boolean involves(Citizen c) {
        return groom.getIdNumber() == c.getIdNumber() || bride.getIdNumber() == c.getIdNumber();
    }
}
