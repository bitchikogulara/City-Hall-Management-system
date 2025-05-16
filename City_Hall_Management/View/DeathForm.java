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
        setSize(500, 350);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("Register Death");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        mainPanel.add(title);
        mainPanel.add(createLabeledField("Enter Citizen ID:", citizenIdField));

        JButton continueBtn = new JButton("Continue");
        continueBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        continueBtn.setBackground(Color.WHITE);
        continueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueBtn.setMaximumSize(new Dimension(200, 40));
        continueBtn.addActionListener(this::loadCitizenAndShowDatePickers);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(continueBtn);

        add(mainPanel);
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

    private void loadCitizenAndShowDatePickers(ActionEvent e) {
        try {
            int id = Integer.parseInt(citizenIdField.getText().trim());
            Citizen c = cityHall.findCitizenById(id);

            if (c == null) {
                JOptionPane.showMessageDialog(this, "No citizen found with the provided ID.");
                return;
            }

            if (c.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "This citizen is already registered as deceased.");
                return;
            }

            if (c.getBirth() == null) {
                JOptionPane.showMessageDialog(this, "This citizen has no birth record.");
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
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("Select Date of Death");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        dayBox = new JComboBox<>();
        monthBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });
        yearBox = new JComboBox<>();

        panel.add(createLabeledField("Day:", dayBox));
        panel.add(createLabeledField("Month:", monthBox));
        panel.add(createLabeledField("Year:", yearBox));

        JButton submitButton = new JButton("Register Death");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        submitButton.setBackground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(new Dimension(200, 40));
        submitButton.addActionListener(this::handleDeath);

        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        monthBox.addActionListener(ev -> updateDayBox());
        yearBox.addActionListener(ev -> updateDayBox());

        add(panel);
        populateDateBoxes();
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
            Date deathDate;
            try {
                deathDate = cal.getTime();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date selected.");
                return;
            }

            if (deathDate.before(selectedCitizen.getBirth().getDate()) || deathDate.after(new Date())) {
                JOptionPane.showMessageDialog(this, "Death date must be between the birth date and today.");
                return;
            }

            int deathId = cityHall.getDeaths().size() + 1;
            Death death = new Death(deathId, deathDate, selectedCitizen, cityHall);
            selectedCitizen.setDeath(death);
            cityHall.addDeath(death);

            JOptionPane.showMessageDialog(this, "Death successfully registered for " + selectedCitizen.getFullName());
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while registering death.");
        }
    }
}
