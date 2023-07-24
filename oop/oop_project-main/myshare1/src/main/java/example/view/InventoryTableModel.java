package example.view;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class InventoryTableModel extends DefaultTableModel {
    // Constructor
    public InventoryTableModel() {
        super();
        // Set the column names for the table
        String[] columnNames = { "ItemId", "Item Name", "Item Price", "Quantity", "Action" };
        this.setColumnIdentifiers(columnNames);
    }

    // Set your data
    public void setData(List<Object[]> data) {
        for (Object[] rowData : data) {
            this.addRow(rowData);
        }
    }



    // Override the isCellEditable method to make cells non-editable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

