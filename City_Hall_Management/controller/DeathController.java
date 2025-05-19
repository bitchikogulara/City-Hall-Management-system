package controller;

import model.*;
import view.DeathForm;

import java.util.Calendar;
import java.util.Date;

public class DeathController {
    private final CityHall cityHall;
    private final DeathForm view;
    private Citizen selectedCitizen;

    public DeathController(CityHall cityHall, DeathForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void loadCitizenAndShowDatePickers(String idText) {
        try {
            int id = Integer.parseInt(idText);
            Citizen c = cityHall.findCitizenById(id);

            if (c == null) {
                view.showMessage("No citizen found with the provided ID.");
                return;
            }

            if (c.getDeath() != null) {
                view.showMessage("This citizen is already registered as deceased.");
                return;
            }

            if (c.getBirth() == null) {
                view.showMessage("This citizen has no birth record.");
                return;
            }

            this.selectedCitizen = c;
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(selectedCitizen.getBirth().getDate());
            view.showDateSelectionUI(this::handleDeath, birthCal);

        } catch (NumberFormatException ex) {
            view.showMessage("Please enter a valid numeric ID.");
        }
    }

    public void handleDeath() {
        try {
            int day = view.getSelectedDay();
            int month = view.getSelectedMonth();
            int year = view.getSelectedYear();

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            Date deathDate;
            try {
                cal.set(year, month, day);
                deathDate = cal.getTime();
            } catch (Exception ex) {
                view.showMessage("Invalid date selected.");
                return;
            }

            if (deathDate.before(selectedCitizen.getBirth().getDate()) || deathDate.after(new Date())) {
                view.showMessage("Death date must be between the birth date and today.");
                return;
            }

            int deathId = cityHall.getDeaths().size() + 1;
            Death death = new Death(deathId, deathDate, selectedCitizen, cityHall);
            selectedCitizen.setDeath(death);
            cityHall.addDeath(death);

            view.showMessage("Death successfully registered for " + selectedCitizen.getFullName());
            view.closeForm();

        } catch (Exception ex) {
            view.showMessage("An error occurred while registering death.");
        }
    }
}
