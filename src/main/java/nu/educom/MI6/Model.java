package nu.educom.MI6;

import javax.swing.*;

public class Model {
    public static boolean[] blacklist = new boolean[999];

    // order of conditions matters because of integer parsing
    public static boolean checkId(String agentInput) {
        return agentInput.length() > 3 || !agentInput.matches("^[0-9]+$") || Integer.parseInt(agentInput) > 956 || Integer.parseInt(agentInput) <= 0 || blacklist[Integer.parseInt(agentInput)];
    }

    public static void setBlacklist(int agentId) {
        blacklist[agentId] = true;
    }
}
