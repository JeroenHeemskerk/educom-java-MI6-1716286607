package nu.educom.MI6;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    boolean[] blacklist = new boolean[999];
    while (true) {
      System.out.println("Welcome, id please:");
      Scanner scanner = new Scanner(System.in);
      StringBuilder agentInput = new StringBuilder(scanner.nextLine());

      while (agentInput.length() > 3 || !(agentInput.toString().matches("^[0-9]+$")) || Integer.parseInt(agentInput.toString()) > 956 || Integer.parseInt(agentInput.toString()) <= 0 || blacklist[Integer.parseInt(agentInput.toString())]) {
        System.out.println("Invalid, try again:");
        agentInput = new StringBuilder(scanner.nextLine());
      }

      int agentId = Integer.parseInt(agentInput.toString());

      while (agentInput.length() < 3) {
        agentInput.insert(0, "0");
      }

      System.out.println("Hello there, " + agentInput);
      StringBuilder agentPassphrase = new StringBuilder(scanner.nextLine());
      if (!(agentPassphrase.toString().equals("For ThE Royal QUEEN"))) {
        System.out.println("Sorry, " + agentInput + ", that is incorrect. You are now blacklisted.");
        blacklist[agentId] = true;
      }
      else {
        System.out.println("Welcome, " + agentInput + ", login successful.");
      }
    }

  }
}