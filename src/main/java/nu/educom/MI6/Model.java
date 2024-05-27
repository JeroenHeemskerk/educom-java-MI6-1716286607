package nu.educom.MI6;

public class Model {
    // order of conditions matters because of integer parsing
    public static boolean checkId(String agentInput) {
        return !agentInput.matches("^[0-9]{1,3}$") || Integer.parseInt(agentInput) > 956 || Integer.parseInt(agentInput) <= 0;
    }
}
