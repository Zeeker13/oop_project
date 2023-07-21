package example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import example.model.User;

public class Login extends JDialog {
    private JTextField users;
    private JTextField ps;
    private JButton lg;
    private JButton acc;
    private JPanel loginPanel;
    private JComboBox<String> comboBox1;

    private User loggedInUser;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(loginPanel, BorderLayout.CENTER);
        setContentPane(contentPane);

        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = users.getText();
                String password = ps.getText();
                String comboBoxValue = (String) comboBox1.getSelectedItem(); // Get the selected role from the combo box

                loggedInUser = getAuthenticatedUser(username, password, comboBoxValue);

                if (loggedInUser != null) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this,
                            "Invalid username or password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        acc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private User getAuthenticatedUser(String username, String password, String comboBoxValue) {
        User user = null;

        final String URL = "jdbc:mysql://localhost:3306/myshare?user=root&password=";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND comboBoxValue = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, comboBoxValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                user = new User(name, email, phone, address, password, comboBoxValue);
                user.setUsername(username);
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        Login loginForm = new Login(null);
        User user = loginForm.loggedInUser;
        if (user != null) {
            System.out.println("          Username: " + user.getUsername());
            System.out.println("          Password: " + user.getPassword());
            System.out.println("          Role: " + user.getSetRole());
        } else {
            System.out.println("Authentication canceled");
        }
    }
}