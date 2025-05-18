package View;

import Controller.DeathController;
import Model.CityHall;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DeathForm extends JFrame {
    private final JTextField citizenIdField = new JTextField();
    private JComboBox<Integer> dayBox;
    private JComboBox<String> monthBox;
    private JComboBox<Integer> yearBox;

    private final DeathController controller;

    public DeathForm(CityHall cityHall) {
        this.controller = new DeathController(cityHall, this);

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
        continueBtn.addActionListener(e -> controller.loadCitizenAndShowDatePickers(citizenIdField.getText().trim()));

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

    public void showDateSelectionUI(Runnable onSubmit, Calendar birthDate) {
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
        submitButton.addActionListener(e -> onSubmit.run());

        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        monthBox.addActionListener(e -> updateDayBox());
        yearBox.addActionListener(e -> updateDayBox());

        add(panel);
        populateDateBoxes(birthDate);
        revalidate();
        repaint();
    }

    private void populateDateBoxes(Calendar birthDate) {
        int birthYear = birthDate.get(Calendar.YEAR);
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

    public int getSelectedDay() {
        return (Integer) dayBox.getSelectedItem();
    }

    public int getSelectedMonth() {
        return monthBox.getSelectedIndex();
    }

    public int getSelectedYear() {
        return (Integer) yearBox.getSelectedItem();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void closeForm() {
        dispose();
    }
}
