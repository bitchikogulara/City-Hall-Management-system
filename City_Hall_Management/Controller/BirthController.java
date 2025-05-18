package Controller;

import Model.*;
import View.BirthForm;

import java.util.Calendar;
import java.util.Date;

public class BirthController {
    private final CityHall cityHall;
    private final BirthForm view;

    public BirthController(CityHall cityHall, BirthForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void handleBirth(String fName, String lName, int day, int month, int year,
                            String fatherText, String motherText, String gender) {
        try {
            if (fName.isEmpty() || lName.isEmpty()) {
                view.showMessage("First name and last name cannot be empty.");
                return;
            }

            if (!fName.matches("[A-Za-z]+") || !lName.matches("[A-Za-z]+")) {
                view.showMessage("Names must contain only letters without spaces or digits.");
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            Date birthDate;
            try {
                cal.set(year, month, day);
                birthDate = cal.getTime();
            } catch (Exception ex) {
                view.showMessage("Invalid birth date selected.");
                return;
            }

            if (birthDate.after(new Date())) {
                view.showMessage("Birth date cannot be in the future.");
                return;
            }

            int fatherId, motherId;
            try {
                fatherId = Integer.parseInt(fatherText);
                motherId = Integer.parseInt(motherText);
            } catch (NumberFormatException ex) {
                view.showMessage("Both parent IDs must be numbers.");
                return;
            }

            Citizen dad = cityHall.findCitizenById(fatherId);
            Citizen mom = cityHall.findCitizenById(motherId);

            if (dad == null) {
                view.showMessage("No citizen found with Father ID: " + fatherId);
                return;
            }
            if (mom == null) {
                view.showMessage("No citizen found with Mother ID: " + motherId);
                return;
            }

            if (!(dad instanceof Male)) {
                view.showMessage("The selected father must be male.");
                return;
            }
            if (!(mom instanceof Female)) {
                view.showMessage("The selected mother must be female.");
                return;
            }

            Male father = (Male) dad;
            Female mother = (Female) mom;

            if (father.getBirth() != null && birthDate.before(father.getBirth().getDate())) {
                view.showMessage("Birth date cannot be before father's birth date.");
                return;
            }

            if (mother.getBirth() != null && birthDate.before(mother.getBirth().getDate())) {
                view.showMessage("Birth date cannot be before mother's birth date.");
                return;
            }

            int newId = cityHall.getCitizens().size() + 100;
            Birth birth = new Birth(newId, birthDate, null, cityHall, father, mother);
            Citizen child;

            if (gender.equals("Male")) {
                child = new Male(newId, fName, lName, birth, cityHall);
            } else {
                child = new Female(newId, fName, lName, birth, cityHall);
            }

            birth = new Birth(newId, birthDate, child, cityHall, father, mother);
            child.setBirth(birth);
            father.addChild(child);
            mother.addChild(child);

            cityHall.addCitizen(child);
            cityHall.addBirth(birth);

            view.showMessage("Birth registered successfully.\nName: " + child.getFullName() + "\nID: " + child.getIdNumber());
            view.closeForm();

        } catch (Exception ex) {
            view.showMessage("An unexpected error occurred. Please check the entered data.");
        }
    }
}
