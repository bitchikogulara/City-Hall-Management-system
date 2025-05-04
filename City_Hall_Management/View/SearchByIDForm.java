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
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("Enter ID:"), BorderLayout.WEST);
        inputPanel.add(idField, BorderLayout.CENTER);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::searchCitizen);
        inputPanel.add(searchButton, BorderLayout.EAST);

        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void searchCitizen(ActionEvent e) {
        resultArea.setText("");

        try {
            int id = Integer.parseInt(idField.getText().trim());
            Citizen citizen = cityHall.findCitizenById(id);
            if (citizen == null) {
                resultArea.setText("No citizen found with ID " + id);
                return;
            }

            resultArea.setText(formatCitizenInfo(citizen));
        } catch (Exception ex) {
            resultArea.setText("Invalid ID format.");
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
            if (c.getBirth().getFather() != null) {
                sb.append("Father: ").append(c.getBirth().getFather().getFullName()).append("\n");
            } else {
                sb.append("Father: Unknown\n");
            }
            if (c.getBirth().getMother() != null) {
                sb.append("Mother: ").append(c.getBirth().getMother().getFullName()).append("\n");
            } else {
                sb.append("Mother: Unknown\n");
            }
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