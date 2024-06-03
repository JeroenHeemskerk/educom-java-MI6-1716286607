package nu.educom.MI6;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.lang.Math;


public class Model {
    int agentId;
    ArrayList<LoginAttempt> loginAttempts;

    public Model(int enteredId) {
        this.agentId = enteredId;
        this.loginAttempts = nu.educom.MI6.Database.readLastLoginAttempts(enteredId);
    }

    public boolean isDenied() {
        int consecutiveFails = loginAttempts.size() - 1;
        // if the last login attempt was successful or has never logged in, agent is not denied
        if (consecutiveFails == -1 || loginAttempts.get(consecutiveFails).getLoginSuccess()) {
            return false;
        }
        // if last login attempt was unsuccessful, check if enough time has passed
        return !LocalDateTime.now().isAfter(loginAttempts.get(consecutiveFails).getLoginStamp().plusMinutes((long) Math.pow(2, consecutiveFails)));
    }

    public Agent authenticateLogin(String passphrase) {
        Agent agent = nu.educom.MI6.Database.readAgentByServiceId(agentId);

        if (isDenied()) {
           return null;
        }
        else if (agent == null || !passphrase.equals(agent.getPassphrase()) || agent.getRetired()) {
            nu.educom.MI6.Database.createLoginAttempt(agentId, false);
            loginAttempts.add(nu.educom.MI6.Database.readLastLoginAttempt(agentId));
            return null;
        }
        else {
            nu.educom.MI6.Database.createLoginAttempt(agentId, true);
            loginAttempts.add(nu.educom.MI6.Database.readLastLoginAttempt(agentId));
            return agent;
        }
    }

    public LocalDateTime getFirstAvailableLoginMoment() {
        int consecutiveFails = loginAttempts.size() - 1;
        // get the last login timestamp and add the lockout time
        return loginAttempts.get(consecutiveFails).getLoginStamp().plusMinutes((long) Math.pow(2, consecutiveFails));
    }
}
