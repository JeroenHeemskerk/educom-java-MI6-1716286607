package nu.educom.MI6;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class Presenter {
    public static void main(String[] args) {
        View view = new View();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.createLoginDialog();
            }
        });
    }

    public void handleLogin(String inputAgent, String passPhrase) {
        View view = new View();
        // order of conditions matters because of integer parsing
        if (!inputAgent.matches("^[0-9]{1,3}$") || Integer.parseInt(inputAgent) > 956 || Integer.parseInt(inputAgent) <= 0) {
            view.handleLoginFailed(Optional.empty());
            return;
        }
        int agentId = Integer.parseInt(inputAgent);
        System.out.println(agentId);
        Model model = new Model(agentId);
        Agent agent = model.authenticateLogin(passPhrase);
        if (agent == null) {
            // get first available login time
            LocalDateTime loginTime = model.getFirstAvailableLoginMoment();
            view.handleLoginFailed(Optional.ofNullable(loginTime));
        } else {
            view.handleLoginSucceed(agent, model.loginAttempts);
        }
    }
}
