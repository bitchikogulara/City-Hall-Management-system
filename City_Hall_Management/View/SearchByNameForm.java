package View;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class SearchByNameForm extends JFrame {
    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> citizenList = new JList<>(listModel);
    private final JTextArea resultArea = new JTextArea();

    private final CityHall cityHall;
    private final List<Citizen> citizens;

    public SearchByNameForm(CityHall cityHall) {
        this.cityHall = cityHall;
        this.citizens = cityHall.getCitizens();

        setTitle("Search Citizen by Name");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        JScrollPane listScroll = new JScrollPane(citizenList);
        listScroll.setPreferredSize(new Dimension(600, 120)); // Smaller list panel

        citizenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setPreferredSize(new Dimension(600, 250)); // Larger result area

        add(topPanel, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);
        add(resultScroll, BorderLayout.SOUTH);

        updateList(citizens);

        searchButton.addActionListener(this::handleSearch);

        citizenList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = citizenList.getSelectedIndex();
                if (index >= 0) {
                    String selected = citizenList.getSelectedValue();
                    int id = Integer.parseInt(selected.substring(selected.indexOf("(ID:") + 5, selected.indexOf(")")));
                    Citizen c = cityHall.findCitizenById(id);
                    resultArea.setText(formatCitizenInfo(c));
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleSearch(ActionEvent e) {
        String text = searchField.getText().toLowerCase().trim();

        List<Citizen> filtered = citizens.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(text))
                .collect(Collectors.toList());

        updateList(filtered);
        resultArea.setText("");
    }

    private void updateList(List<Citizen> list) {
        listModel.clear();
        for (Citizen c : list) {
            listModel.addElement(c.getFullName() + " (ID: " + c.getIdNumber() + ")");
        }
    }

    private String formatCitizenInfo(Citizen c) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(c.getIdNumber()).append("\n");
        sb.append("Name: ").append(c.getFullName()).append("\n");
        sb.append("Gender: ").append(c instanceof Male ? "Male" : "Female").append("\n");

        if (c.getBirth() != null) {
            sb.append("Birth Date: ").append(sdf.format(c.getBirth().getDate())).append("\n");
            sb.append("Father: ").append(c.getBirth().getFather() != null ? c.getBirth().getFather().getFullName() : "Unknown").append("\n");
            sb.append("Mother: ").append(c.getBirth().getMother() != null ? c.getBirth().getMother().getFullName() : "Unknown").append("\n");
        } else {
            sb.append("Birth Date: N/A\n");
            sb.append("Father: Unknown\n");
            sb.append("Mother: Unknown\n");
        }

        sb.append("Status: ").append(c.getDeath() != null ? "Deceased" : "Alive").append("\n");

        String spouseId = "Single";
        if (c instanceof Male male) {
            for (Marriage m : male.getMarriages()) {
                if (m.isActive()) {
                    spouseId = String.valueOf(m.getBride().getIdNumber());
                    break;
                }
            }
        } else if (c instanceof Female female) {
            for (Marriage m : female.getMarriages()) {
                if (m.isActive()) {
                    spouseId = String.valueOf(m.getGroom().getIdNumber());
                    break;
                }
            }
        }

        sb.append("Spouse ID: ").append(spouseId).append("\n");

        return sb.toString();
    }
}