package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public class DivorceForm extends JFrame {
    private final JTextField citizenIdField = new JTextField();
    private final CityHall cityHall;

    public DivorceForm(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Register Divorce");
        setSize(450, 220);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel title = new JLabel("Register Divorce");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        mainPanel.add(title);
        mainPanel.add(createLabeledField("Citizen ID:", citizenIdField));

        JButton submitButton = new JButton("Divorce");
        submitButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        submitButton.setBackground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setMaximumSize(new Dimension(200, 40));
        submitButton.addActionListener(this::handleDivorce);

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

    private void handleDivorce(ActionEvent e) {
        try {
            String input = citizenIdField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a citizen ID.");
                return;
            }

            int citizenId = Integer.parseInt(input);
            Citizen citizen = cityHall.findCitizenById(citizenId);

            if (citizen == null) {
                JOptionPane.showMessageDialog(this, "No citizen found with the given ID.");
                return;
            }

            List<Marriage> marriages = citizen instanceof Male
                    ? ((Male) citizen).getMarriages()
                    : ((Female) citizen).getMarriages();

            Marriage activeMarriage = null;
            for (Marriage m : marriages) {
                if (m.isActive()) {
                    activeMarriage = m;
                    break;
                }
            }

            if (activeMarriage == null) {
                JOptionPane.showMessageDialog(this, "This citizen is not currently married.");
                return;
            }

            int divorceId = activeMarriage.getMarriageId(); // reuse marriageId for simplicity
            Divorce divorce = new Divorce(divorceId, new Date(), activeMarriage);
            activeMarriage.setDivorce(divorce);

            JOptionPane.showMessageDialog(this, "Divorce successfully registered between " +
                    activeMarriage.getGroom().getFullName() + " and " +
                    activeMarriage.getBride().getFullName() + ".");

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Citizen ID must be a valid number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.");
        }
    }
}
