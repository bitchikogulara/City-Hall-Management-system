package View;

import Model.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class BirthForm extends JFrame {
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

    public BirthForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Register New Birth");
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
        add(new JLabel("Father ID:"));
        add(fatherIdField);
        add(new JLabel("Mother ID:"));
        add(motherIdField);
        add(new JLabel("Gender:"));
        add(genderBox);

        JButton submitButton = new JButton("Register Birth");
        submitButton.addActionListener(this::handleBirth);
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

        int currentDay = dayBox.getSelectedItem() != null ? (Integer) dayBox.getSelectedItem() : 1;

        dayBox.removeAllItems();
        for (int d = 1; d <= maxDay; d++) {
            dayBox.addItem(d);
        }

        if (currentDay <= maxDay) {
            dayBox.setSelectedItem(currentDay);
        }
    }

    private void handleBirth(ActionEvent e) {
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

            int fatherId = Integer.parseInt(fatherIdField.getText().trim());
            int motherId = Integer.parseInt(motherIdField.getText().trim());

            Citizen dad = cityHall.findCitizenById(fatherId);
            Citizen mom = cityHall.findCitizenById(motherId);

            if (!(dad instanceof Male)) {
                JOptionPane.showMessageDialog(this, "Invalid father: must be a Male.");
                return;
            }

            if (!(mom instanceof Female)) {
                JOptionPane.showMessageDialog(this, "Invalid mother: must be a Female.");
                return;
            }

            Male father = (Male) dad;
            Female mother = (Female) mom;

            if (father.getBirth() != null && birthDate.before(father.getBirth().getDate())) {
                JOptionPane.showMessageDialog(this, "Birth date cannot be before father's birth date.");
                return;
            }

            if (mother.getBirth() != null && birthDate.before(mother.getBirth().getDate())) {
                JOptionPane.showMessageDialog(this, "Birth date cannot be before mother's birth date.");
                return;
            }

            int newId = cityHall.getCitizens().size() + 100;
            Citizen child;

            Birth birth = new Birth(newId, birthDate, null, cityHall, father, mother);

            if (genderBox.getSelectedItem().equals("Male")) {
                child = new Male(newId, fName, lName, birth, cityHall);
            } else {
                child = new Female(newId, fName, lName, birth, cityHall);
            }

            birth = new Birth(newId, birthDate, child, cityHall, father, mother);
            child.setBirth(birth);
            father.addChild(child);
            mother.addChild(child);

            cityHall.addCitizen(child);
            cityHall.addBirth(birth);

            JOptionPane.showMessageDialog(this, "Birth registered: " + child.getFullName() + " (ID: " + child.getIdNumber() + ")");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
