package Controller;

import Model.*;
import View.SearchByNameForm;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByNameController {
    private final CityHall cityHall;
    private final SearchByNameForm view;

    public SearchByNameController(CityHall cityHall, SearchByNameForm view) {
        this.cityHall = cityHall;
        this.view = view;
    }

    public void handleSearch(String text) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter a name or part of a name to search.");
            return;
        }

        List<Citizen> filtered = cityHall.getCitizens().stream()
                .filter(c -> c.getFullName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            view.updateList(List.of());
            view.setResult("No citizens found matching your search.");
        } else {
            List<String> entries = filtered.stream()
                    .map(c -> c.getFullName() + " (ID: " + c.getIdNumber() + ")")
                    .collect(Collectors.toList());
            view.updateList(entries);
            view.clearResult();
        }
    }

    public void handleSelection(String selected) {
        if (selected == null) return;
        try {
            int id = Integer.parseInt(selected.substring(selected.indexOf("(ID:") + 5, selected.indexOf(")")));
            Citizen c = cityHall.findCitizenById(id);
            if (c != null) {
                view.setResult(formatCitizenInfo(c));
            }
        } catch (Exception ignored) {}
    }

    public void updateList(List<Citizen> citizens) {
        List<String> entries = citizens.stream()
                .map(c -> c.getFullName() + " (ID: " + c.getIdNumber() + ")")
                .collect(Collectors.toList());
        view.updateList(entries);
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
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced On: " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        } else if (c instanceof Female female) {
            if (female.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : female.getMarriages()) {
                sb.append("  Spouse: ").append(m.getGroom().getFullName()).append(" (ID: ").append(m.getGroom().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced On: " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        }

        return sb.toString();
    }
}
