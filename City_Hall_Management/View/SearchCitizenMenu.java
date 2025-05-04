package View;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SearchCitizenMenu extends JFrame {
    private final CityHall cityHall;

    public SearchCitizenMenu(CityHall cityHall) {
        this.cityHall = cityHall;

        setTitle("Search Citizen");
        setSize(300, 120);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton searchByIdBtn = new JButton("Search by ID");
        JButton searchByNameBtn = new JButton("Search by Name");

        searchByIdBtn.addActionListener(this::openSearchById);
        searchByNameBtn.addActionListener(this::openSearchByName);

        add(searchByIdBtn);
        add(searchByNameBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openSearchById(ActionEvent e) {
        new SearchByIDForm(cityHall);
        dispose();
    }

    private void openSearchByName(ActionEvent e) {
        new SearchByNameForm(cityHall);
        dispose();
    }
}
