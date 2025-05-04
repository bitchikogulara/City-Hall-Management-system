package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class AddCitizenForm extends JFrame {
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField fatherIdField = new JTextField();
    private final JTextField motherIdField = new JTextField();
    private final JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female"});

    private final JComboBox<Integer> dayBox = new JComboBox<>();
    private final JComboBox<String> monthBox = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    });
    private final JComboBox<Integer> yearBox = new JComboBox<>();

    private final CityHall cityHall;

    public AddCitizenForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Add New Citizen");
        setSize(400, 350);
        setLayout(new GridLayout(9, 2, 10, 5));

        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Birth Day:"));
        add(dayBox);
        add(new JLabel("Birth Month:"));
        add(monthBox);
        add(new JLabel("Birth Year:"));
        add(yearBox);
        add(new JLabel("Father ID (optional):"));
        add(fatherIdField);
        add(new JLabel("Mother ID (optional):"));
        add(motherIdField);
        add(new JLabel("Gender:"));
        add(genderBox);

        JButton submitButton = new JButton("Add Citizen");
        submitButton.addActionListener(this::handleAddCitizen);
        add(new JLabel());
        add(submitButton);

        populateYearBox();
        monthBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        yearBox.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR));
        updateDayBox();

        monthBox.addActionListener(e -> updateDayBox());
        yearBox.addActionListener(e -> updateDayBox());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateYearBox() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = currentYear; y >= 1900; y--) {
            yearBox.addItem(y);
        }
    }

    private void updateDayBox() {
        int year = (Integer) yearBox.getSelectedItem();
        int month = monthBox.getSelectedIndex();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int selected = dayBox.getSelectedItem() != null ? (Integer) dayBox.getSelectedItem() : 1;

        dayBox.removeAllItems();
        for (int d = 1; d <= maxDay; d++) {
            dayBox.addItem(d);
        }

        if (selected <= maxDay) {
            dayBox.setSelectedItem(selected);
        }
    }

    private void handleAddCitizen(ActionEvent e) {
        try {
            String fName = firstNameField.getText().trim();
            String lName = lastNameField.getText().trim();

            int day = (Integer) dayBox.getSelectedItem();
            int month = monthBox.getSelectedIndex();
            int year = (Integer) yearBox.getSelectedItem();

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(year, month, day);
            Date birthDate = cal.getTime();

            if (birthDate.after(new Date())) {
                JOptionPane.showMessageDialog(this, "Birth date cannot be in the future.");
                return;
            }

            Male father = null;
            Female mother = null;

            String fatherText = fatherIdField.getText().trim();
            String motherText = motherIdField.getText().trim();

            if (!fatherText.isEmpty()) {
                Citizen c = cityHall.findCitizenById(Integer.parseInt(fatherText));
                if (c instanceof Male) {
                    father = (Male) c;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid father ID (must be a Male)");
                    return;
                }
            }

            if (!motherText.isEmpty()) {
                Citizen c = cityHall.findCitizenById(Integer.parseInt(motherText));
                if (c instanceof Female) {
                    mother = (Female) c;
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid mother ID (must be a Female)");
                    return;
                }
            }

            int id = cityHall.getCitizens().size() + 100;
            Birth birth = new Birth(id, birthDate, null, cityHall, father, mother);

            Citizen newCitizen;
            if (genderBox.getSelectedItem().equals("Male")) {
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

            JOptionPane.showMessageDialog(this, "Citizen added: " + newCitizen.getFullName() + " (ID: " + newCitizen.getIdNumber() + ")");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
