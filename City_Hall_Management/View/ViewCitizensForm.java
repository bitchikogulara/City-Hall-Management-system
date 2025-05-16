package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ViewCitizensForm extends JFrame implements UpdateListener {
    private final CityHall cityHall;
    private DefaultTableModel tableModel;

    public ViewCitizensForm(CityHall cityHall) {
        this.cityHall = cityHall;
        cityHall.registerListener(this);  // Register to receive updates

        setTitle("All Registered Citizens");
        setSize(1000, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("All Registered Citizens");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 60, 110));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Full Name", "Gender", "Birth Date",
                "Father", "Mother", "Status", "Spouse ID"
        };

        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        loadData(); // Initial load
    }

    private void loadData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //System.out.println("here load");

        for (Citizen c : cityHall.getCitizens()) {
            //System.out.println("here for");
            String id = String.valueOf(c.getIdNumber());
            String name = c.getFullName();
            String gender = c instanceof Male ? "Male" : "Female";

            String birthDate = "N/A";
            String fatherName = "Unknown";
            String motherName = "Unknown";

            if (c.getBirth() != null) {
                birthDate = sdf.format(c.getBirth().getDate());
                if (c.getBirth().getFather() != null)
                    fatherName = c.getBirth().getFather().getFullName();
                if (c.getBirth().getMother() != null)
                    motherName = c.getBirth().getMother().getFullName();
            }

            String lifeStatus = (c.getDeath() != null) ? "Deceased" : "Alive";

            String spouseId = "Single";
            //System.out.println("here 1");
            if (c instanceof Male male) {
                //System.out.println("here 2.1");
                for (Marriage m : male.getMarriages()) {
                    if (m.isActive()) {
                        spouseId = String.valueOf(m.getBride().getIdNumber());
                        break;
                    }
                }
            } else if (c instanceof Female female) {
                //System.out.println("here 2.2");
                for (Marriage m : female.getMarriages()) {
                    if (m.isActive()) {
                        spouseId = String.valueOf(m.getGroom().getIdNumber());
                        break;
                    }
                }
            }

            tableModel.addRow(new Object[]{
                    id, name, gender, birthDate, fatherName, motherName, lifeStatus, spouseId
            });
        }
    }

    @Override
    public void onDataUpdated() {
        loadData();
    }

    @Override
    public void dispose() {
        cityHall.unregisterListener(this);
        super.dispose();
    }
}  
