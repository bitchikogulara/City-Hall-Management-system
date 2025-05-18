package View;

import Controller.SearchByNameController;
import Model.CityHall;

import javax.swing.*;
import java.awt.*;

public class SearchByNameForm extends JFrame {
    private final JTextField searchField = new JTextField();
    private final JButton searchButton = new JButton("Search");
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> citizenList = new JList<>(listModel);
    private final JTextArea resultArea = new JTextArea();
    private final SearchByNameController controller;

    public SearchByNameForm(CityHall cityHall) {
        this.controller = new SearchByNameController(cityHall, this);

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
        searchButton.addActionListener(e -> controller.handleSearch(searchField.getText().trim()));
        searchPanel.add(searchButton, BorderLayout.EAST);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        citizenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        citizenList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = citizenList.getSelectedValue();
                controller.handleSelection(selected);
            }
        });

        add(mainPanel);
        setVisible(true);

        controller.updateList(cityHall.getCitizens());
    }

    public void clearResult() {
        resultArea.setText("");
    }

    public void setResult(String text) {
        resultArea.setText(text);
    }

    public void updateList(java.util.List<String> entries) {
        listModel.clear();
        for (String entry : entries) {
            listModel.addElement(entry);
        }
    }
}
