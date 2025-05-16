package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class SearchByIDForm extends JFrame {
    private final JTextField idField = new JTextField();
    private final JTextArea resultArea = new JTextArea();
    private final CityHall cityHall;

    public SearchByIDForm(CityHall cityHall) {
        this.cityHall = cityHall;

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
        searchButton.addActionListener(this::searchCitizen);
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

    private void searchCitizen(ActionEvent e) {
        resultArea.setText("");
        String input = idField.getText().trim();

        if (input.isEmpty()) {
            resultArea.setText("Please enter a citizen ID.");
            return;
        }

        try {
            int id = Integer.parseInt(input);
            Citizen citizen = cityHall.findCitizenById(id);

            if (citizen == null) {
                resultArea.setText("No citizen found with ID " + id + ".");
                return;
            }

            resultArea.setText(formatCitizenInfo(citizen));
        } catch (NumberFormatException ex) {
            resultArea.setText("Citizen ID must be a number.");
        } catch (Exception ex) {
            resultArea.setText("An unexpected error occurred.");
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
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced on " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        } else if (c instanceof Female female) {
            if (female.getMarriages().isEmpty()) {
                sb.append("  No marriages recorded.\n");
            }
            for (Marriage m : female.getMarriages()) {
                sb.append("  Spouse: ").append(m.getGroom().getFullName()).append(" (ID: ").append(m.getGroom().getIdNumber()).append(")\n");
                sb.append("  Married On: ").append(sdf.format(m.getDate())).append("\n");
                sb.append("  Status: ").append(m.getDivorce() != null ? "Divorced on " + sdf.format(m.getDivorce().getDate()) : "Active").append("\n\n");
            }
        }

        return sb.toString();
    }
}
