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
        setSize(400, 450);
        setLayout(new GridLayout(8, 1, 10, 10));

        JButton addCitizenBtn = new JButton("Add Citizen");
        JButton viewCitizensBtn = new JButton("View Citizens");
        JButton marriageBtn = new JButton("Marry Citizens");
        JButton divorceBtn = new JButton("Divorce Citizen");
        JButton birthBtn = new JButton("Register Birth");
        JButton deathBtn = new JButton("Register Death");
        JButton searchCitizenBtn = new JButton("Search Citizen");
        JButton exitBtn = new JButton("Exit");

        add(addCitizenBtn);
        add(viewCitizensBtn);
        add(marriageBtn);
        add(divorceBtn);
        add(birthBtn);
        add(deathBtn);
        add(searchCitizenBtn);
        add(exitBtn);

        addCitizenBtn.addActionListener(this::openAddCitizenForm);
        viewCitizensBtn.addActionListener(this::openViewCitizens);
        marriageBtn.addActionListener(this::openMarriageForm);
        divorceBtn.addActionListener(this::openDivorceForm);
        birthBtn.addActionListener(this::openBirthForm);
        deathBtn.addActionListener(this::openDeathForm);
        searchCitizenBtn.addActionListener(this::openSearchCitizenForm);
        exitBtn.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openAddCitizenForm(ActionEvent e) {
        new AddCitizenForm(cityHall);
    }

    private void openViewCitizens(ActionEvent e) {
        new ViewCitizensForm(cityHall);
    }

    private void openMarriageForm(ActionEvent e) {
        new MarriageForm(cityHall);
    }

    private void openDivorceForm(ActionEvent e) {
        new DivorceForm(cityHall);
    }

    private void openBirthForm(ActionEvent e) {
        new BirthForm(cityHall);
    }

    private void openDeathForm(ActionEvent e) {
        new DeathForm(cityHall);
    }

    private void openSearchCitizenForm(ActionEvent e) {
        new SearchCitizenMenu(cityHall);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CityHall cityHall = new CityHall("Évry");
            new Main(cityHall);
        });
    }
}