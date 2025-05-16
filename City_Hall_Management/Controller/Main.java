package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    private final CityHall cityHall;

    public Main(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("City Hall Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container panel with background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(230, 240, 255)); // light pastel blue
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Title label
        JLabel title = new JLabel("City Hall Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(new Color(30, 60, 110)); // deep blue
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(title);

        // Buttons
        String[] labels = {
                "Add Citizen", "View Citizens", "Marry Citizens", "Divorce Citizen",
                "Register Birth", "Register Death", "Search Citizen", "Exit"
        };

        for (String label : labels) {
            JButton button = createStyledButton(label);
            mainPanel.add(button);
            mainPanel.add(Box.createVerticalStrut(12));

            // Link button actions
            button.addActionListener(e -> handleAction(label));
        }

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(30, 30, 30));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 45));
        button.setMaximumSize(new Dimension(300, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        return button;
    }

    private void handleAction(String action) {
        switch (action) {
            case "Add Citizen" -> new AddCitizenForm(cityHall);
            case "View Citizens" -> new ViewCitizensForm(cityHall);
            case "Marry Citizens" -> new MarriageForm(cityHall);
            case "Divorce Citizen" -> new DivorceForm(cityHall);
            case "Register Birth" -> new BirthForm(cityHall);
            case "Register Death" -> new DeathForm(cityHall);
            case "Search Citizen" -> new SearchCitizenMenu(cityHall);
            case "Exit" -> System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CityHall cityHall = new CityHall("Évry");
            new Main(cityHall);
        });
    }
}
