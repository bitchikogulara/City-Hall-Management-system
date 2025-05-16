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
        submitButton.addActionListener(this::handleMarriage);

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

    private void handleMarriage(ActionEvent e) {
        try {
            String groomInput = groomIdField.getText().trim();
            String brideInput = brideIdField.getText().trim();

            if (groomInput.isEmpty() || brideInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Groom and Bride IDs.");
                return;
            }

            int groomId = Integer.parseInt(groomInput);
            int brideId = Integer.parseInt(brideInput);

            Citizen g = cityHall.findCitizenById(groomId);
            Citizen b = cityHall.findCitizenById(brideId);

            if (g == null || b == null) {
                JOptionPane.showMessageDialog(this, "One or both citizens were not found. Please check the IDs.");
                return;
            }

            if (!(g instanceof Male)) {
                JOptionPane.showMessageDialog(this, "Groom must be a male citizen.");
                return;
            }

            if (!(b instanceof Female)) {
                JOptionPane.showMessageDialog(this, "Bride must be a female citizen.");
                return;
            }

            Male groom = (Male) g;
            Female bride = (Female) b;

            if (groom.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "The groom is deceased and cannot marry.");
                return;
            }

            if (bride.getDeath() != null) {
                JOptionPane.showMessageDialog(this, "The bride is deceased and cannot marry.");
                return;
            }

            for (Marriage m : groom.getMarriages()) {
                if (m.isActive()) {
                    JOptionPane.showMessageDialog(this, "The groom is already married.");
                    return;
                }
            }

            for (Marriage m : bride.getMarriages()) {
                if (m.isActive()) {
                    JOptionPane.showMessageDialog(this, "The bride is already married.");
                    return;
                }
            }

            int marriageId = cityHall.getMarriages().size() + 1;
            Marriage marriage = new Marriage(marriageId, groom, bride, new Date(), cityHall);
            
            groom.addMarriage(marriage);
            bride.addMarriage(marriage);
            cityHall.addMarriage(marriage);

            JOptionPane.showMessageDialog(this, "Marriage successfully registered between " +
                    groom.getFullName() + " and " + bride.getFullName() + ".");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IDs must be numeric values.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.");
        }
    }
}
