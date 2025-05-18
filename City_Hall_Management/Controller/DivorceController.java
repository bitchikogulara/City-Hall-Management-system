package Controller;

import Model.*;
import View.DivorceForm;

import java.util.Date;
import java.util.List;

public class DivorceController {
    private final CityHall cityHall;
    private final DivorceForm view;

    public DivorceController(CityHall cityHall, DivorceForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void handleDivorce(String idText) {
        try {
            if (idText.isEmpty()) {
                view.showMessage("Please enter a citizen ID.");
                return;
            }

            int citizenId = Integer.parseInt(idText);
            Citizen citizen = cityHall.findCitizenById(citizenId);

            if (citizen == null) {
                view.showMessage("No citizen found with the given ID.");
                return;
            }

            List<Marriage> marriages = citizen instanceof Male
                    ? ((Male) citizen).getMarriages()
                    : ((Female) citizen).getMarriages();

            Marriage activeMarriage = null;
            for (Marriage m : marriages) {
                if (m.isActive()) {
                    activeMarriage = m;
                    break;
                }
            }

            if (activeMarriage == null) {
                view.showMessage("This citizen is not currently married.");
                return;
            }

            int divorceId = activeMarriage.getMarriageId(); // reuse marriageId
            Divorce divorce = new Divorce(divorceId, new Date(), activeMarriage);
            activeMarriage.setDivorce(divorce);
            cityHall.addDivorce(divorce);

            view.showMessage("Divorce successfully registered between " +
                    activeMarriage.getGroom().getFullName() + " and " +
                    activeMarriage.getBride().getFullName() + ".");
            view.closeForm();

        } catch (NumberFormatException ex) {
            view.showMessage("Citizen ID must be a valid number.");
        } catch (Exception ex) {
            view.showMessage("An unexpected error occurred.");
        }
    }
}
