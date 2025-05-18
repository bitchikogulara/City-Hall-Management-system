package View;

import Controller.SearchByIDController;
import Model.CityHall;

import javax.swing.*;
import java.awt.*;

public class SearchByIDForm extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextArea resultArea = new JTextArea();
    private final SearchByIDController controller;

    public SearchByIDForm(CityHall cityHall) {
        this.controller = new SearchByIDController(cityHall, this);

        setTitle("Search Citizen by ID");
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Search Citizen by ID");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 60, 110));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel idLabel = new JLabel("Enter ID:");
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputPanel.add(idLabel, BorderLayout.WEST);

        idField.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(idField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.addActionListener(e -> controller.searchCitizenById(idField.getText().trim()));
        inputPanel.add(searchButton, BorderLayout.EAST);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(440, 200));

        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public void setResultText(String text) {
        resultArea.setText(text);
    }
}
