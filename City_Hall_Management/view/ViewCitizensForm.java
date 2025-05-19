package view;

import model.CityHall;
import model.UpdateListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ViewCitizensController;

import java.awt.*;

public class ViewCitizensForm extends JFrame implements UpdateListener {
    private final CityHall cityHall;
    private final ViewCitizensController controller;
    private DefaultTableModel tableModel;

    public ViewCitizensForm(CityHall cityHall) {
        this.cityHall = cityHall;
        this.controller = new ViewCitizensController(cityHall, this);
        cityHall.registerListener(this);

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

        controller.loadData(tableModel); // Initial load
    }

    @Override
    public void onDataUpdated() {
        controller.loadData(tableModel);
    }

    @Override
    public void dispose() {
        cityHall.unregisterListener(this);
        super.dispose();
    }
}
