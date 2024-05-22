package nu.educom.MI6;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    boolean[] blacklist = new boolean[999];
    while (true) {
      JFrame idFrame = new JFrame("MI6 id question");

      // ondanks deze instelling, gaat het programma door als de popup gecanceld wordt
      idFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // prompt the user to enter their id
      String agentInput = JOptionPane.showInputDialog(idFrame, "Welcome, id please:",
              "MI6 login",
              JOptionPane.QUESTION_MESSAGE);

      // order of conditions matters because of integer parsing
      while (agentInput.length() > 3 || !(agentInput.matches("^[0-9]+$")) || Integer.parseInt(agentInput) > 956 || Integer.parseInt(agentInput) <= 0 || blacklist[Integer.parseInt(agentInput)]) {
        agentInput = JOptionPane.showInputDialog(idFrame, "Invalid id, try again:",
                "MI6 login",
                JOptionPane.ERROR_MESSAGE);
      }

      StringBuilder agentPadded = new StringBuilder(agentInput);
      while (agentPadded.length() < 3) {
        agentPadded.insert(0, "0");
      }

      JFrame phraseFrame = new JFrame("MI6 phrase question");
      phraseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      String agentPassphrase = JOptionPane.showInputDialog(phraseFrame, "Hello there, " + agentPadded + ", please enter the passphrase: ", "MI6 login", JOptionPane.QUESTION_MESSAGE);
      if (!(agentPassphrase.equals("For ThE Royal QUEEN"))) {
        JOptionPane.showMessageDialog(null, "Sorry, " + agentPadded + ", that is incorrect. You are now blacklisted.", "Warning", JOptionPane.WARNING_MESSAGE);
        blacklist[Integer.parseInt(agentInput)] = true;
      }
      else {
        JOptionPane.showMessageDialog(null, "Welcome, " + agentPadded + ", login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println();
      }
    }

  }
}