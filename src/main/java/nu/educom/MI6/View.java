package nu.educom.MI6;

import com.mysql.cj.log.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class View {
    public static String padAgent(String agentInput) {
        // pad the id with 0s
        StringBuilder agentPadded = new StringBuilder(agentInput);
        while (agentPadded.length() < 3) {
            agentPadded.insert(0, "0");
        }
        return agentPadded.toString();
    }

    public static void createLoginDialog() {
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

                // Use SwingWorker to handle the login process in the background
                new LoginWorker(frame, idText, passphraseText, idField, phraseField).execute();
            }
        });

        // Settings for the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static class LoginWorker extends SwingWorker<Void, Void> {
        private final JFrame frame;
        private final String idText;
        private final String passphraseText;
        private final JTextField idField;
        private final JPasswordField phraseField;
        private boolean loginSuccess;
        private boolean validLicence;
        private LocalDate licenceExpiration;
        private LocalDateTime lockoutTime;
        private ArrayList<LoginAttempt> loginAttempts;

        public LoginWorker(JFrame frame, String idText, String passphraseText, JTextField idField, JPasswordField phraseField) {
            this.frame = frame;
            this.idText = idText;
            this.passphraseText = passphraseText;
            this.idField = idField;
            this.phraseField = phraseField;
        }

        @Override
        protected Void doInBackground() throws Exception {
            if (!nu.educom.MI6.Model.checkId(idText)) {
                int enteredId = Integer.parseInt(idText);

                loginSuccess = nu.educom.MI6.Database.authenticateLogin(enteredId, passphraseText);
                if (loginSuccess) {
                    loginAttempts = nu.educom.MI6.Database.getLastLoginAttempts(enteredId);
                    nu.educom.MI6.Database.createLoginAttempt(enteredId, true);
                    loginAttempts.add(nu.educom.MI6.Database.getLastLoginAttempt(enteredId));

                    Agent agent = nu.educom.MI6.Database.readAgentByServiceId(enteredId);
                    validLicence = agent.getLicence();
                    licenceExpiration = agent.getLicenceValid();
                } else {
                    lockoutTime = nu.educom.MI6.Database.getFirstAvailableLoginMoment(enteredId);
                }
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                get(); // Ensure any exceptions thrown in doInBackground() are propagated

                if (!nu.educom.MI6.Model.checkId(idText)) {
                    if (loginSuccess) {
                        createAttemptsDialog(frame, loginAttempts);

                        JFrame messageFrame = new JFrame("Access Granted");
                        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        messageFrame.setSize(350, 200);
                        messageFrame.setLayout(new BorderLayout());
                        String message = validLicence ? "Access Granted. Your licence expires on: " + licenceExpiration : "Access Granted.";
                        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
                        JButton closeButton = new JButton("Close");
                        closeButton.addActionListener(e -> messageFrame.dispose());

                        messageFrame.add(messageLabel, BorderLayout.CENTER);
                        messageFrame.add(closeButton, BorderLayout.SOUTH);
                        messageFrame.setLocationRelativeTo(frame);
                        messageFrame.setVisible(true);

                        idField.setText("");
                        phraseField.setText("");
                    } else {
                        JFrame errorFrame = new JFrame("Error");
                        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        errorFrame.setSize(350, 200);
                        errorFrame.setLayout(new BorderLayout());
                        JLabel errorLabel = new JLabel("Access DENIED. Locked out until: " + lockoutTime, SwingConstants.CENTER);
                        JButton closeButton = new JButton("Close");
                        closeButton.addActionListener(e -> errorFrame.dispose());

                        errorFrame.add(errorLabel, BorderLayout.CENTER);
                        errorFrame.add(closeButton, BorderLayout.SOUTH);
                        errorFrame.setLocationRelativeTo(frame);
                        errorFrame.setVisible(true);
                    }
                } else {
                    JFrame errorFrame = new JFrame("Error");
                    errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    errorFrame.setSize(350, 200);
                    errorFrame.setLayout(new BorderLayout());
                    JLabel errorLabel = new JLabel("Access DENIED", SwingConstants.CENTER);
                    JButton closeButton = new JButton("Close");
                    closeButton.addActionListener(e -> errorFrame.dispose());

                    errorFrame.add(errorLabel, BorderLayout.CENTER);
                    errorFrame.add(closeButton, BorderLayout.SOUTH);
                    errorFrame.setLocationRelativeTo(frame);
                    errorFrame.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();

                JFrame errorFrame = new JFrame("Error");
                errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                errorFrame.setSize(350, 200);
                errorFrame.setLayout(new BorderLayout());
                JLabel errorLabel = new JLabel("An error occurred during login.", SwingConstants.CENTER);
                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(err -> errorFrame.dispose());

                errorFrame.add(errorLabel, BorderLayout.CENTER);
                errorFrame.add(closeButton, BorderLayout.SOUTH);
                errorFrame.setLocationRelativeTo(frame);
                errorFrame.setVisible(true);
            }
        }

    }

    public static void createAttemptsDialog(JFrame frame, ArrayList<LoginAttempt> loginAttempts) {
        // Create column names for JTable
        String[] columnNames = {"Date", "Time", "Success"};

        // Create data array for JTable
        Object[][] data = new Object[loginAttempts.size()][3];

        // Fill data array iteratively
        for (int i = 0; i < loginAttempts.size(); i++) {
            LoginAttempt attempt = loginAttempts.get(i);
            data[i][0] = attempt.getLoginStamp().toLocalDate();
            data[i][1] = attempt.getLoginStamp().toLocalTime();
            data[i][2] = attempt.getLoginSuccess();
        }

        // Create table model and JTable
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a new JFrame to display login attempts
        JFrame attemptsFrame = new JFrame("Login Attempts");
        attemptsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        attemptsFrame.setSize(400, 300);
        attemptsFrame.setLayout(new BorderLayout());

        attemptsFrame.add(scrollPane, BorderLayout.CENTER);

        // Add a close button to the frame
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> attemptsFrame.dispose());
        attemptsFrame.add(closeButton, BorderLayout.SOUTH);

        attemptsFrame.setLocationRelativeTo(frame);
        attemptsFrame.setVisible(true);
    }

}

