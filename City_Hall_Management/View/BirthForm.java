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
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("Register New Birth");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(30, 60, 110));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(title);

        mainPanel.add(createLabeledField("First Name:", firstNameField));
        mainPanel.add(createLabeledField("Last Name:", lastNameField));
        mainPanel.add(createLabeledField("Birth Day:", dayBox));
        mainPanel.add(createLabeledField("Birth Month:", monthBox));
        mainPanel.add(createLabeledField("Birth Year:", yearBox));
        mainPanel.add(createLabeledField("Father ID:", fatherIdField));
        mainPanel.add(createLabeledField("Mother ID:", motherIdField));
        mainPanel.add(createLabeledField("Gender:", genderBox));

        JButton submitButton = new JButton("Register Birth");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        submitButton.setBackground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(new Dimension(200, 40));
        submitButton.addActionListener(this::handleBirth);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(submitButton);

        add(mainPanel);

        populateYearBox();
        monthBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        yearBox.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR));
        updateDayBox();

        monthBox.addActionListener(e -> updateDayBox());
        yearBox.addActionListener(e -> updateDayBox());

        setVisible(true);
    }

    private JPanel createLabeledField(String label, JComponent input) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(240, 248, 255));
        JLabel l = new JLabel(label);
        l.setPreferredSize(new Dimension(150, 30));
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(l, BorderLayout.WEST);
        input.setPreferredSize(new Dimension(200, 30));
        panel.add(input, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(400, 40));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return panel;
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

            if (fName.isEmpty() || lName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name and last name cannot be empty.");
                return;
            }

            if (!fName.matches("[A-Za-z]+") || !lName.matches("[A-Za-z]+")) {
                JOptionPane.showMessageDialog(this, "Names must contain only letters without spaces or digits.");
                return;
            }

            int day = (Integer) dayBox.getSelectedItem();
            int month = monthBox.getSelectedIndex();
            int year = (Integer) yearBox.getSelectedItem();

            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(year, month, day);
            Date birthDate;
            try {
                birthDate = cal.getTime();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid birth date selected.");
                return;
            }

            if (birthDate.after(new Date())) {
                JOptionPane.showMessageDialog(this, "Birth date cannot be in the future.");
                return;
            }

            int fatherId, motherId;
            try {
                fatherId = Integer.parseInt(fatherIdField.getText().trim());
                motherId = Integer.parseInt(motherIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Both parent IDs must be numbers.");
                return;
            }

            Citizen dad = cityHall.findCitizenById(fatherId);
            Citizen mom = cityHall.findCitizenById(motherId);

            if (dad == null) {
                JOptionPane.showMessageDialog(this, "No citizen found with Father ID: " + fatherId);
                return;
            }
            if (mom == null) {
                JOptionPane.showMessageDialog(this, "No citizen found with Mother ID: " + motherId);
                return;
            }

            if (!(dad instanceof Male)) {
                JOptionPane.showMessageDialog(this, "The selected father must be male.");
                return;
            }

            if (!(mom instanceof Female)) {
                JOptionPane.showMessageDialog(this, "The selected mother must be female.");
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

            JOptionPane.showMessageDialog(this, "Birth registered successfully.\nName: " + child.getFullName() + "\nID: " + child.getIdNumber());
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please check the entered data.");
        }
    }
}
