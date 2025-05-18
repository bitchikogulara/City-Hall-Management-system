package Controller;

import Model.*;

import java.util.Calendar;
import java.util.Date;
import View.AddCitizenForm;

public class AddCitizenController {
    private final CityHall cityHall;
    private final AddCitizenForm view;

    public AddCitizenController(CityHall cityHall, AddCitizenForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void handleAddCitizen(String fName, String lName, int day, int month, int year,
                                 String gender, String fatherText, String motherText) {
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
            cal.set(year, month, day);
            Date birthDate;
            try {
                birthDate = cal.getTime();
            } catch (Exception ex) {
                view.showMessage("Invalid birth date selected.");
                return;
            }

            if (birthDate.after(new Date())) {
                view.showMessage("Birth date cannot be in the future.");
                return;
            }

            Male father = null;
            Female mother = null;

            if (!fatherText.isEmpty()) {
                try {
                    int fatherId = Integer.parseInt(fatherText);
                    Citizen c = cityHall.findCitizenById(fatherId);
                    if (c == null) {
                        view.showMessage("No citizen found with Father ID: " + fatherId);
                        return;
                    }
                    if (c instanceof Male) {
                        father = (Male) c;
                    } else {
                        view.showMessage("The selected father must be male.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showMessage("Father ID must be a number.");
                    return;
                }
            }

            if (!motherText.isEmpty()) {
                try {
                    int motherId = Integer.parseInt(motherText);
                    Citizen c = cityHall.findCitizenById(motherId);
                    if (c == null) {
                        view.showMessage("No citizen found with Mother ID: " + motherId);
                        return;
                    }
                    if (c instanceof Female) {
                        mother = (Female) c;
                    } else {
                        view.showMessage("The selected mother must be female.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showMessage("Mother ID must be a number.");
                    return;
                }
            }

            int id = cityHall.getCitizens().size() + 100;
            Birth birth = new Birth(id, birthDate, null, cityHall, father, mother);

            Citizen newCitizen;
            if (gender.equals("Male")) {
                newCitizen = new Male(id, fName, lName, birth, cityHall);
            } else {
                newCitizen = new Female(id, fName, lName, birth, cityHall);
            }

            birth = new Birth(id, birthDate, newCitizen, cityHall, father, mother);
            newCitizen.setBirth(birth);

            if (father != null) father.addChild(newCitizen);
            if (mother != null) mother.addChild(newCitizen);

            cityHall.addCitizen(newCitizen);
            cityHall.addBirth(birth);

            view.showMessage("Citizen added successfully.\nName: " + newCitizen.getFullName() + "\nID: " + newCitizen.getIdNumber());
            view.closeForm();

        } catch (Exception ex) {
            view.showMessage("An unexpected error occurred. Please check the entered data.");
        }
    }
}
