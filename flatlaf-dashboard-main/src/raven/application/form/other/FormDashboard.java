package raven.application.form.other;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import raven.model.Client;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormDashboard extends JPanel {

    private DefaultTableModel tableModel;
    private JTable clientTable;
    private JScrollPane tableScrollPane;
    private JLabel lb;
    private JButton addClientButton, editClientButton, deleteClientButton;
    private List<Client> clients;

    public FormDashboard() {
        initComponents();
        setupClientTable();
        loadSampleData();
    }

    private void initComponents() {
        // Title Label
        lb = new JLabel("Tableau de Bord", SwingConstants.CENTER);
        lb.setFont(new Font("Arial", Font.BOLD, 24));

        // Add Client Button (at the top)
        addClientButton = new JButton("Add Client");
        addClientButton.addActionListener(evt -> addClientActionPerformed());

        // Set up layout
        setLayout(new BorderLayout());

        // Add top panel with title and "Add Client" button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lb, BorderLayout.NORTH);
        topPanel.add(addClientButton, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
    }

    private void setupClientTable() {
        // Create the table model with columns
        String[] columns = {"ID", "Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        // Create the JTable and set its model
        clientTable = new JTable(tableModel);

        // Add the table to a scroll pane
        tableScrollPane = new JScrollPane(clientTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Initialize the clients list
        clients = new ArrayList<>();

        // Add action buttons (Edit and Delete) below the table
        editClientButton = new JButton("Edit Client");
        deleteClientButton = new JButton("Delete Client");

        // Add action listeners
        editClientButton.addActionListener(evt -> editClientActionPerformed());
        deleteClientButton.addActionListener(evt -> deleteClientActionPerformed());

        // Button panel for Edit and Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editClientButton);
        buttonPanel.add(deleteClientButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSampleData() {
        // Add sample client data
        clients.add(new Client(1, "John Doe", "john@example.com", "123-456-7890", "123 Main St"));
        clients.add(new Client(2, "Jane Smith", "jane@example.com", "098-765-4321", "456 Oak Ave"));

        // Populate the table
        for (Client client : clients) {
            tableModel.addRow(new Object[]{
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getAddress()
            });
        }
    }

    private void addClientActionPerformed() {
        // Prompt user for client information
        String name = JOptionPane.showInputDialog(this, "Enter client name:");
        if (name != null && !name.trim().isEmpty()) {
            String email = JOptionPane.showInputDialog(this, "Enter client email:");
            String phone = JOptionPane.showInputDialog(this, "Enter client phone:");
            String address = JOptionPane.showInputDialog(this, "Enter client address:");

            int newId = clients.size() + 1;
            Client newClient = new Client(newId, name, email, phone, address);
            clients.add(newClient);

            tableModel.addRow(new Object[]{
                    newClient.getId(),
                    newClient.getName(),
                    newClient.getEmail(),
                    newClient.getPhone(),
                    newClient.getAddress()
            });

            JOptionPane.showMessageDialog(this, "Client added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editClientActionPerformed() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow >= 0) {
            Client client = clients.get(selectedRow);

            // Prompt user for updated information
            String name = JOptionPane.showInputDialog(this, "Enter new name:", client.getName());
            if (name != null) {
                String email = JOptionPane.showInputDialog(this, "Enter new email:", client.getEmail());
                String phone = JOptionPane.showInputDialog(this, "Enter new phone:", client.getPhone());
                String address = JOptionPane.showInputDialog(this, "Enter new address:", client.getAddress());

                client.setName(name);
                client.setEmail(email);
                client.setPhone(phone);
                client.setAddress(address);

                // Update the table
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(email, selectedRow, 2);
                tableModel.setValueAt(phone, selectedRow, 3);
                tableModel.setValueAt(address, selectedRow, 4);

                JOptionPane.showMessageDialog(this, "Client updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteClientActionPerformed() {
        int selectedRow = clientTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this client?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                clients.remove(selectedRow);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Client deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a client to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
