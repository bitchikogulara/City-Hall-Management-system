package model;

import java.io.Serializable;
import java.util.*;

public class Divorce implements Serializable{
    private static final long serialVersionUID = 1L;
    private int divorceId;
    private Date date;
    private Marriage marriage;

    public Divorce(int divorceId, Date date, Marriage marriage) {
        this.divorceId = divorceId;
        this.date = date;
        this.marriage = marriage;
    }

    public int getDivorceId() {
        return divorceId;
    }

    public Date getDate() {
        return date;
    }

    public Marriage getMarriage() {
        return marriage;
    }
}
