package view;

import model.CityHall;

import javax.swing.*;

import controller.SearchCitizenMenuController;

import java.awt.*;

public class SearchCitizenMenu extends JFrame {
    private final CityHall cityHall;
    private final SearchCitizenMenuController controller;

    public SearchCitizenMenu(CityHall cityHall) {
        this.cityHall = cityHall;
        this.controller = new SearchCitizenMenuController(cityHall, this);

        setTitle("Search Citizen");
        setSize(350, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel title = new JLabel("Choose Search Method");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(title);

        JButton searchByIdBtn = new JButton("Search by ID");
        JButton searchByNameBtn = new JButton("Search by Name");

        for (JButton button : new JButton[]{searchByIdBtn, searchByNameBtn}) {
            button.setFont(new Font("SansSerif", Font.PLAIN, 15));
            button.setBackground(Color.WHITE);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40));
            button.setPreferredSize(new Dimension(200, 40));
            button.setFocusPainted(false);
            mainPanel.add(button);
            mainPanel.add(Box.createVerticalStrut(15));
        }

        searchByIdBtn.addActionListener(e -> controller.openSearchById());
        searchByNameBtn.addActionListener(e -> controller.openSearchByName());

        add(mainPanel);
        setVisible(true);
    }

    public void closeWindow() {
        dispose();
    }
}
