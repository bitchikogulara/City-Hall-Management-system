package view;

import model.CityHall;

import javax.swing.*;

import controller.BirthController;

import java.awt.*;
import java.util.Calendar;

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

    private final BirthController controller;

    public BirthForm(CityHall cityHall) {
        this.controller = new BirthController(cityHall, this);

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
        submitButton.addActionListener(e -> controller.handleBirth(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                (Integer) dayBox.getSelectedItem(),
                monthBox.getSelectedIndex(),
                (Integer) yearBox.getSelectedItem(),
                fatherIdField.getText().trim(),
                motherIdField.getText().trim(),
                (String) genderBox.getSelectedItem()
        ));

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

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void closeForm() {
        dispose();
    }
}