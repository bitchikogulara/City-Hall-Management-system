package view;

import model.CityHall;

import javax.swing.*;

import controller.MarriageController;

import java.awt.*;

public class MarriageForm extends JFrame {
    private final JTextField groomIdField = new JTextField();
    private final JTextField brideIdField = new JTextField();
    private final MarriageController controller;

    public MarriageForm(CityHall cityHall) {
        this.controller = new MarriageController(cityHall, this);

        setTitle("Register Marriage");
        setSize(450, 250);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("Register Marriage");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(title);

        mainPanel.add(createLabeledField("Groom ID:", groomIdField));
        mainPanel.add(createLabeledField("Bride ID:", brideIdField));

        JButton submitButton = new JButton("Register Marriage");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        submitButton.setBackground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(new Dimension(220, 40));
        submitButton.addActionListener(e ->
                controller.handleMarriage(groomIdField.getText().trim(), brideIdField.getText().trim()));

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(submitButton);

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

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void closeForm() {
        dispose();
    }
}
