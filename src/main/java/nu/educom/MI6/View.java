package nu.educom.MI6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {
    public static String createDialog(String frameName, String frameTitle, String frameLabel, String messageType) {
        JFrame frame = new JFrame(frameName);
        // Ensure the JFrame is hidden (not visible) because we will use JDialog
        frame.setVisible(false);

        JDialog dialog = new JDialog(frame, frameName, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // VIEW
        // prompt the agent to enter their id
        int msgType = switch (messageType) {
            case "question" -> JOptionPane.QUESTION_MESSAGE;
            case "information" -> JOptionPane.INFORMATION_MESSAGE;
            case "warning" -> JOptionPane.WARNING_MESSAGE;
            case "plain" -> JOptionPane.PLAIN_MESSAGE;
            default -> JOptionPane.ERROR_MESSAGE;
        };
        return JOptionPane.showInputDialog(dialog, frameLabel, frameTitle, msgType);
    }

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
                String enteredId = idField.getText();
                String enteredPassphrase = new String(phraseField.getPassword());

                if (nu.educom.MI6.Model.checkLogin(enteredId, enteredPassphrase)) {
                    JOptionPane.showMessageDialog(frame, "Access Granted");
                    idField.setText("");
                    phraseField.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Access DENIED", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Settings for the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
