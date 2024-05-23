package nu.educom.MI6;
import javax.swing.*;

// Main class is the controller
public class Main {
  public static void main(String[] args) {
    System.out.println(nu.educom.MI6.Agent.getLicenceByServiceId(7));
    System.out.println(nu.educom.MI6.Agent.getLicenceDateByServiceId(7));
    System.out.println(nu.educom.MI6.Agent.getRetiredByServiceId(7));
    System.out.println(nu.educom.MI6.Agent.getRetiredByServiceId(777));
    nu.educom.MI6.View.createLoginDialog();
//    while () {
//      // Ik twijfel nog of deze functie te algemeen is. Misschien createIdquestionDialog() oid?
//      // create a dialog for the id
//      String agentInput = nu.educom.MI6.View.createDialog("Mi6 id question", "MI6 login", "Welcome, id please:", "question");
//      checkExit(agentInput);
//
//      while (nu.educom.MI6.Model.checkId(agentInput)) {
//        agentInput = nu.educom.MI6.View.createDialog("Mi6 id question", "MI6 login", "Invalid id, try again:", "warning");
//        checkExit(agentInput);
//      }
//
//      // pad the id with 0s
//      String agentPadded = nu.educom.MI6.View.padAgent(agentInput);
//
//      // create a dialog for the passphrase
//      String agentPassphrase = nu.educom.MI6.View.createDialog("MI6 phrase question", "MI6 login", "Hello there, " + agentPadded + ", please enter the passphrase: ", "question");
//      checkExit(agentPassphrase);
//
//      // blacklist agents that do not obey the queen
//      if (!(agentPassphrase.equals("For ThE Royal QUEEN"))) {
//        JOptionPane.showMessageDialog(null, "Sorry, " + agentPadded + ", that is incorrect. You are now blacklisted.", "Warning", JOptionPane.WARNING_MESSAGE);
//        Model.setBlacklist(Integer.parseInt(agentInput));
//      }
//      // inform the user of successful login
//      else {
//        JOptionPane.showMessageDialog(null, "Welcome, " + agentPadded + ", login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
//      }
    }

  static void checkExit(String input) {
    // exit when input is null
    if (input == null) {
      System.exit(0);
    }
  }
}