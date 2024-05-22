package nu.educom.MI6;
import javax.swing.*;

// Main class is the controller
public class Main {
  public static void main(String[] args) {
    while (true) {
      // VIEW
      JFrame idFrame = new JFrame("MI6 id question");
      // ondanks deze instelling, gaat het programma door als de popup gecanceld wordt
      idFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // VIEW
      // prompt the agent to enter their id
      String agentInput = JOptionPane.showInputDialog(idFrame, "Welcome, id please:",
              "MI6 login",
              JOptionPane.QUESTION_MESSAGE);

      while (nu.educom.MI6.Model.checkId(agentInput)) {
        agentInput = JOptionPane.showInputDialog(idFrame, "Invalid id, try again:",
                "MI6 login",
                JOptionPane.ERROR_MESSAGE);
      }

      // VIEW
      // pad the id with 0s
      StringBuilder agentPadded = new StringBuilder(agentInput);
      while (agentPadded.length() < 3) {
        agentPadded.insert(0, "0");
      }

      // VIEW
      JFrame phraseFrame = new JFrame("MI6 phrase question");
      phraseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // VIEW
      // prompt the agent to enter the passphrase
      String agentPassphrase = JOptionPane.showInputDialog(phraseFrame, "Hello there, " + agentPadded + ", please enter the passphrase: ", "MI6 login", JOptionPane.QUESTION_MESSAGE);

      // CONTROLLER
      // blacklist agents that do not obey the queen
      if (!(agentPassphrase.equals("For ThE Royal QUEEN"))) {
        // VIEW
        JOptionPane.showMessageDialog(null, "Sorry, " + agentPadded + ", that is incorrect. You are now blacklisted.", "Warning", JOptionPane.WARNING_MESSAGE);
        // MODEL
        Model.setBlacklist(Integer.parseInt(agentInput));
      }

      // inform the user of successful login
      else {
        // VIEW
        JOptionPane.showMessageDialog(null, "Welcome, " + agentPadded + ", login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
}