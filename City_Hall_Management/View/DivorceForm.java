package View;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public class DivorceForm extends JFrame {
    private final JTextField citizenIdField = new JTextField();
    private final CityHall cityHall;

    public DivorceForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Register Divorce");
        setSize(400, 150);
        setLayout(new GridLayout(2, 2, 10, 5));

        add(new JLabel("Citizen ID:"));
        add(citizenIdField);

        JButton submitButton = new JButton("Divorce");
        submitButton.addActionListener(this::handleDivorce);
        add(new JLabel());
        add(submitButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleDivorce(ActionEvent e) {
        try {
            int citizenId = Integer.parseInt(citizenIdField.getText().trim());
            Citizen citizen = cityHall.findCitizenById(citizenId);

            if (citizen == null) {
                JOptionPane.showMessageDialog(this, "Citizen not found.");
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
                JOptionPane.showMessageDialog(this, "Citizen is not currently married.");
                return;
            }

            int divorceId = activeMarriage.getMarriageId(); // reuse marriageId for simplicity
            Divorce divorce = new Divorce(divorceId, new Date(), activeMarriage);
            activeMarriage.setDivorce(divorce);

            JOptionPane.showMessageDialog(this, "Divorce registered between " +
                    activeMarriage.getGroom().getFullName() + " and " +
                    activeMarriage.getBride().getFullName());

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
