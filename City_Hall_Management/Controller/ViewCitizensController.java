package Controller;

import Model.*;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public class ViewCitizensController {
    private final CityHall cityHall;

    public ViewCitizensController(CityHall cityHall, UpdateListener listener) {
        this.cityHall = cityHall;
    }

    public void loadData(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Citizen c : cityHall.getCitizens()) {
            String id = String.valueOf(c.getIdNumber());
            String name = c.getFullName();
            String gender = c instanceof Male ? "Male" : "Female";

            String birthDate = "N/A";
            String fatherName = "Unknown";
            String motherName = "Unknown";

            if (c.getBirth() != null) {
                birthDate = sdf.format(c.getBirth().getDate());
                if (c.getBirth().getFather() != null)
                    fatherName = c.getBirth().getFather().getFullName();
                if (c.getBirth().getMother() != null)
                    motherName = c.getBirth().getMother().getFullName();
            }

            String lifeStatus = (c.getDeath() != null) ? "Deceased" : "Alive";

            String spouseId = "Single";
            if (c instanceof Male male) {
                for (Marriage m : male.getMarriages()) {
                    if (m.isActive()) {
                        spouseId = String.valueOf(m.getBride().getIdNumber());
                        break;
                    }
                }
            } else if (c instanceof Female female) {
                for (Marriage m : female.getMarriages()) {
                    if (m.isActive()) {
                        spouseId = String.valueOf(m.getGroom().getIdNumber());
                        break;
                    }
                }
            }

            tableModel.addRow(new Object[]{
                    id, name, gender, birthDate, fatherName, motherName, lifeStatus, spouseId
            });
        }
    }
}
