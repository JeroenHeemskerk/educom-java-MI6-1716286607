package nu.educom.MI6;
import javax.swing.*;

// Main class is the controller
public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        nu.educom.MI6.View.createLoginDialog();
      }
    });

  }
}