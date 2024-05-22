package nu.educom.MI6;

import javax.swing.*;

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
}
