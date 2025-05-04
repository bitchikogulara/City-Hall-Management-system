package View;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class MarriageForm extends JFrame {
    private final JTextField groomIdField = new JTextField();
    private final JTextField brideIdField = new JTextField();

    private final CityHall cityHall;

    public MarriageForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Register Marriage");
        setSize(400, 200);
        setLayout(new GridLayout(3, 2, 10, 5));

        add(new JLabel("Groom ID:"));
        add(groomIdField);
        add(new JLabel("Bride ID:"));
        add(brideIdField);

        JButton submitButton = new JButton("Register Marriage");
        submitButton.addActionListener(this::handleMarriage);
        add(new JLabel());
        add(submitButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleMarriage(ActionEvent e) {
        try {
            int groomId = Integer.parseInt(groomIdField.getText().trim());
            int brideId = Integer.parseInt(brideIdField.getText().trim());

            Citizen g = cityHall.findCitizenById(groomId);
            Citizen b = cityHall.findCitizenById(brideId);

            if (!(g instanceof Male)) {
                JOptionPane.showMessageDialog(this, "Groom must be a Male.");
                return;
            }

            if (!(b instanceof Female)) {
                JOptionPane.showMessageDialog(this, "Bride must be a Female.");
                return;
            }

            Male groom = (Male) g;
            Female bride = (Female) b;

            if (groom.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "Groom is deceased and cannot marry.");
                return;
            }

            if (bride.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "Bride is deceased and cannot marry.");
                return;
            }

            for (Marriage m : groom.getMarriages()) {
                if (m.isActive()) {
                    JOptionPane.showMessageDialog(this, "Groom is already married.");
                    return;
                }
            }

            for (Marriage m : bride.getMarriages()) {
                if (m.isActive()) {
                    JOptionPane.showMessageDialog(this, "Bride is already married.");
                    return;
                }
            }

            int marriageId = cityHall.getMarriages().size() + 1;
            Marriage marriage = new Marriage(marriageId, groom, bride, new Date(), cityHall);
            cityHall.addMarriage(marriage);
            groom.addMarriage(marriage);
            bride.addMarriage(marriage);

            JOptionPane.showMessageDialog(this, "Marriage registered between " + groom.getFullName() + " and " + bride.getFullName());
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}