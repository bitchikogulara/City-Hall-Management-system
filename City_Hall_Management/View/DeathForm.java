package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class DeathForm extends JFrame {
    private final JTextField citizenIdField = new JTextField();
    private JComboBox<Integer> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;

    private final CityHall cityHall;
    private Citizen selectedCitizen;

    public DeathForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Register Death");
        setSize(400, 250);
        setLayout(new GridLayout(3, 2, 10, 5));

        add(new JLabel("Enter Citizen ID:"));
        add(citizenIdField);

        JButton continueBtn = new JButton("Continue");
        continueBtn.addActionListener(this::loadCitizenAndShowDatePickers);
        add(new JLabel());
        add(continueBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCitizenAndShowDatePickers(ActionEvent e) {
        try {
            int id = Integer.parseInt(citizenIdField.getText().trim());
            Citizen c = cityHall.findCitizenById(id);

            if (c == null) {
                JOptionPane.showMessageDialog(this, "Citizen not found.");
                return;
            }

            if (c.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "Citizen is already marked as deceased.");
                return;
            }

            if (c.getBirth() == null) {
                JOptionPane.showMessageDialog(this, "Citizen has no birth record.");
                return;
            }

            this.selectedCitizen = c;
            showDateSelectionUI();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.");
        }
    }

    private void showDateSelectionUI() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 2, 10, 5));

        add(new JLabel("Day:"));
        dayBox = new JComboBox<>();
        add(dayBox);

        add(new JLabel("Month:"));
        monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });
        add(monthBox);

        add(new JLabel("Year:"));
        yearBox = new JComboBox<>();
        add(yearBox);

        JButton submitButton = new JButton("Register Death");
        submitButton.addActionListener(this::handleDeath);
        add(new JLabel());
        add(submitButton);

        populateDateBoxes();

        monthBox.addActionListener(ev -> updateDayBox());
        yearBox.addActionListener(ev -> updateDayBox());

        revalidate();
        repaint();
    }

    private void populateDateBoxes() {
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(selectedCitizen.getBirth().getDate());

        int birthYear = birthCal.get(Calendar.YEAR);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        yearBox.removeAllItems();
        for (int y = birthYear; y <= currentYear; y++) {
            yearBox.addItem(y);
        }

        yearBox.setSelectedItem(currentYear);
        monthBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

        updateDayBox();
    }

    private void updateDayBox() {
        if (yearBox.getSelectedItem() == null) return;

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

    private void handleDeath(ActionEvent e) {
        try {
            int day = (Integer) dayBox.getSelectedItem();
            int month = monthBox.getSelectedIndex();
            int year = (Integer) yearBox.getSelectedItem();

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(year, month, day);
            Date deathDate = cal.getTime();

            if (deathDate.before(selectedCitizen.getBirth().getDate()) || deathDate.after(new Date())) {
                JOptionPane.showMessageDialog(this, "Death date must be between birth and today.");
                return;
            }

            int deathId = cityHall.getDeaths().size() + 1;
            Death death = new Death(deathId, deathDate, selectedCitizen, cityHall);
            selectedCitizen.setDeath(death);
            cityHall.addDeath(death);

            JOptionPane.showMessageDialog(this, "Death registered for " + selectedCitizen.getFullName());
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date.");
        }
    }
}
