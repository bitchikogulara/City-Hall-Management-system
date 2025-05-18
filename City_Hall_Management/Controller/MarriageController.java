package Controller;

import Model.*;
import View.MarriageForm;

import java.util.Date;

public class MarriageController {
    private final CityHall cityHall;
    private final MarriageForm view;

    public MarriageController(CityHall cityHall, MarriageForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void handleMarriage(String groomInput, String brideInput) {
        try {
            if (groomInput.isEmpty() || brideInput.isEmpty()) {
                view.showMessage("Please enter both Groom and Bride IDs.");
                return;
            }

            int groomId = Integer.parseInt(groomInput);
            int brideId = Integer.parseInt(brideInput);

            Citizen g = cityHall.findCitizenById(groomId);
            Citizen b = cityHall.findCitizenById(brideId);

            if (g == null || b == null) {
                view.showMessage("One or both citizens were not found. Please check the IDs.");
                return;
            }

            if (!(g instanceof Male)) {
                view.showMessage("Groom must be a male citizen.");
                return;
            }

            if (!(b instanceof Female)) {
                view.showMessage("Bride must be a female citizen.");
                return;
            }

            Male groom = (Male) g;
            Female bride = (Female) b;

            if (groom.getDeath() != null) {
                view.showMessage("The groom is deceased and cannot marry.");
                return;
            }

            if (bride.getDeath() != null) {
                view.showMessage("The bride is deceased and cannot marry.");
                return;
            }

            for (Marriage m : groom.getMarriages()) {
                if (m.isActive()) {
                    view.showMessage("The groom is already married.");
                    return;
                }
            }

            for (Marriage m : bride.getMarriages()) {
                if (m.isActive()) {
                    view.showMessage("The bride is already married.");
                    return;
                }
            }

            int marriageId = cityHall.getMarriages().size() + 1;
            Marriage marriage = new Marriage(marriageId, groom, bride, new Date(), cityHall);

            groom.addMarriage(marriage);
            bride.addMarriage(marriage);
            cityHall.addMarriage(marriage);

            view.showMessage("Marriage successfully registered between " +
                    groom.getFullName() + " and " + bride.getFullName() + ".");
            view.closeForm();

        } catch (NumberFormatException ex) {
            view.showMessage("IDs must be numeric values.");
        } catch (Exception ex) {
            view.showMessage("An unexpected error occurred.");
        }
    }
}
