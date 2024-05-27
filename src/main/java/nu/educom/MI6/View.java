package nu.educom.MI6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class View {
    Presenter presenter = new Presenter();

    public String padAgent(String agentInput) {
        // pad the id with 0s
        StringBuilder agentPadded = new StringBuilder(agentInput);
        while (agentPadded.length() < 3) {
            agentPadded.insert(0, "0");
        }
        return agentPadded.toString();
    }

    public void createLoginDialog() {
        JFrame frame = new JFrame("MI6 login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new java.awt.Dimension(250, 250));
        frame.setVisible(true);

        JPanel idPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JPanel phrasePanel = new JPanel(new GridLayout(2, 1, 10, 10));

        JLabel idLabel = new JLabel("ID: ");
        JLabel phraseLabel = new JLabel("Passphrase: ");
        JTextField idField = new JTextField("");
        JPasswordField phraseField = new JPasswordField("");

        JButton submitBtn = new JButton("Login");

        frame.setLayout(new GridLayout(3, 1, 10, 10));
        idPanel.add(idLabel);
        idPanel.add(idField);
        frame.add(idPanel);
        phrasePanel.add(phraseLabel);
        phrasePanel.add(phraseField);
        frame.add(phrasePanel);
        frame.add(submitBtn);

        // add ActionListener to the submit button
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idField.getText();
                String passphraseText = new String(phraseField.getPassword());

                presenter.handleLogin(idText, passphraseText);
            }
        });

        // Settings for the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void handleLoginSucceed(Agent agent, ArrayList<LoginAttempt> loginAttempts) {
        JFrame frame = new JFrame("Access");
        // make JPanel iteratively
        showLoginAttemptsTable(frame, loginAttempts);

        // get agent licence information
        boolean licence = agent.getLicence();
        if (licence) {
            LocalDate expirationDate = agent.getLicenceValid();
            JOptionPane.showMessageDialog(frame, "Access Granted. Your licence expires on: " + expirationDate);
        } else {
            JOptionPane.showMessageDialog(frame, "Access Granted. ");
        }
    }

    public void handleLoginFailed(Optional<LocalDateTime> loginTime) {
        StringBuilder sb = new StringBuilder("Access DENIED");
        loginTime.ifPresent(localDateTime -> sb.append(". Locked out until: ").append(localDateTime));
        JFrame frame = new JFrame("Denied");
        JOptionPane.showMessageDialog(frame, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showLoginAttemptsTable(JFrame frame, ArrayList<LoginAttempt> loginAttempts) {
        // create column names for JTable
        String[] columnNames = {"Date", "Time", "Success"};

        // create data array for JTable
        Object[][] data = new Object[loginAttempts.size()][3];
        for (int i = 0; i < loginAttempts.size(); i++) {
            LoginAttempt attempt = loginAttempts.get(i);
            data[i][0] = attempt.getLoginStamp().toLocalDate();
            data[i][1] = attempt.getLoginStamp().toLocalTime();
            data[i][2] = attempt.getLoginSuccess();
        }

        // create table model and JTable
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // create a new dialog to display login attempts
        JDialog attemptsDialog = new JDialog(frame, "Login Attempts", true);
        attemptsDialog.add(scrollPane);
        attemptsDialog.setSize(400, 300);
        attemptsDialog.setLocationRelativeTo(frame);
        attemptsDialog.setVisible(true);
    }

}

