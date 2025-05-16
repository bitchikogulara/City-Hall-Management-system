package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;
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
        setSize(600, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Search Citizen by Name");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 60, 110));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        mainPanel.add(title);

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(new Color(240, 248, 255));
        searchField.setPreferredSize(new Dimension(200, 30));
        searchPanel.add(searchField, BorderLayout.CENTER);

        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchButton.setBackground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchPanel.add(searchButton, BorderLayout.EAST);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        citizenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        citizenList.setVisibleRowCount(6);
        JScrollPane listScroll = new JScrollPane(citizenList);
        listScroll.setPreferredSize(new Dimension(500, 100));
        mainPanel.add(listScroll);
        mainPanel.add(Box.createVerticalStrut(10));

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setPreferredSize(new Dimension(500, 260));
        mainPanel.add(resultScroll);

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

        add(mainPanel);
        setVisible(true);
    }

    private void handleSearch(ActionEvent e) {
        String text = searchField.getText().toLowerCase().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name or part of a name to search.");
            return;
        }

        List<Citizen> filtered = citizens.stream()
                .filter(c -> c.getFullName().toLowerCase().contains(text))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            listModel.clear();
            resultArea.setText("No citizens found matching your search.");
        } else {
            updateList(filtered);
            resultArea.setText("");
        }
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
            sb.append("Birth Date: N/A\nFather: Unknown\nMother: Unknown\n");
        }

        sb.append("Status: ").append(c.getDeath() != null ? "Deceased" : "Alive").append("\n");

        if (c.getDeath() != null) {
            sb.append("Death Date: ").append(sdf.format(c.getDeath().getDate())).append("\n");
        }

        sb.append("Marriage History:\n");

        if (c instanceof Male male) {
            if (male.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : male.getMarriages()) {
                sb.append("  Spouse: ").append(m.getBride().getFullName()).append(" (ID: ").append(m.getBride().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                if (m.getDivorce() != null) {
                    sb.append("  Divorced On: ").append(sdf.format(m.getDivorce().getDate())).append("\n");
                } else {
                    sb.append("  Status: Active\n");
                }
                sb.append("\n");
            }
        } else if (c instanceof Female female) {
            if (female.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : female.getMarriages()) {
                sb.append("  Spouse: ").append(m.getGroom().getFullName()).append(" (ID: ").append(m.getGroom().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                if (m.getDivorce() != null) {
                    sb.append("  Divorced On: ").append(sdf.format(m.getDivorce().getDate())).append("\n");
                } else {
                    sb.append("  Status: Active\n");
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
