package Controller;

import Model.*;
import View.SearchByIDForm;

import java.text.SimpleDateFormat;

public class SearchByIDController {
    private final CityHall cityHall;
    private final SearchByIDForm view;

    public SearchByIDController(CityHall cityHall, SearchByIDForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void searchCitizenById(String input) {
        if (input.isEmpty()) {
            view.setResultText("Please enter a citizen ID.");
            return;
        }

        try {
            int id = Integer.parseInt(input);
            Citizen citizen = cityHall.findCitizenById(id);

            if (citizen == null) {
                view.setResultText("No citizen found with ID " + id + ".");
                return;
            }

            view.setResultText(formatCitizenInfo(citizen));

        } catch (NumberFormatException ex) {
            view.setResultText("Citizen ID must be a number.");
        } catch (Exception ex) {
            view.setResultText("An unexpected error occurred.");
        }
    }

    private String formatCitizenInfo(Citizen c) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(c.getIdNumber()).append("\n");
        sb.append("Name: ").append(c.getFullName()).append("\n");
        sb.append("Gender: ").append(c instanceof Male ? "Male" : "Female").append("\n");

        if (c.getBirth() != null) {
            sb.append("Birth Date: ").append(sdf.format(c.getBirth().getDate())).append("\n");
            sb.append("Father: ").append(c.getBirth().getFather() != null ? c.getBirth().getFather().getFullName() : "Unknown").append("\n");
            sb.append("Mother: ").append(c.getBirth().getMother() != null ? c.getBirth().getMother().getFullName() : "Unknown").append("\n");
        } else {
            sb.append("Birth Date: N/A\nFather: Unknown\nMother: Unknown\n");
        }

        sb.append("Status: ").append(c.getDeath() != null ? "Deceased" : "Alive").append("\n");

        if (c.getDeath() != null) {
            sb.append("Death Date: ").append(sdf.format(c.getDeath().getDate())).append("\n");
        }

        sb.append("Marriage History:\n");

        if (c instanceof Male male) {
            if (male.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : male.getMarriages()) {
                sb.append("  Spouse: ").append(m.getBride().getFullName()).append(" (ID: ").append(m.getBride().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced on " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        } else if (c instanceof Female female) {
            if (female.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : female.getMarriages()) {
                sb.append("  Spouse: ").append(m.getGroom().getFullName()).append(" (ID: ").append(m.getGroom().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced on " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        }

        return sb.toString();
    }
}
