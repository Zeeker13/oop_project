package example.view;

import example.config.Db;
import example.model.Inventory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryList extends JFrame {
    private JTable table;
    private InventoryTableModel tableModel;

    public InventoryList() {
        // Create the inventory list panel and add components to it
        JPanel inventoryListPanel = new JPanel(new BorderLayout());

        // Create the InventoryTableModel and set it to the JTable
        tableModel = new InventoryTableModel();
        table = new JTable(tableModel);

        // Add the JTable to a JScrollPane to enable scrolling if necessary
        JScrollPane scrollPane = new JScrollPane(table);
        inventoryListPanel.add(scrollPane, BorderLayout.CENTER);

        // Set up JFrame properties and add the inventory list panel to the JFrame's content pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setTitle("Current Inventories");
        add(inventoryListPanel);
        setVisible(true);

        // Fetch inventory data from the database and populate the table
        initData();
    }

    private void initData() {
        // Fetch inventory data from the database
        Db db = Db.getSingleInstance();
        List<Inventory> inventoryList = db.getInventoryList();

        // Populate the table with inventory data
        List<Object[]> data = new ArrayList<>();
        for (Inventory inventory : inventoryList) {
            Object[] rowData = { inventory.getItemId(), inventory.getItemName(), inventory.getItemPrice(), inventory.getQuantity(), "Update" };
            data.add(rowData);
        }
        tableModel.setData(data);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryList::new);
    }
}