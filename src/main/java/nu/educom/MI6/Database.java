package nu.educom.MI6;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.lang.Math;

public class Database {
    public static Agent readAgentByServiceId(int serviceId) {
        String query = "SELECT * FROM agents WHERE service_id = ?";

        try (Connection connection = MySQLConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, serviceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    int servId = resultSet.getInt("service_id");
                    boolean licence = resultSet.getBoolean("licence_to_kill");
                    boolean retired = resultSet.getBoolean("retired");
                    LocalDate licenceValid = resultSet.getDate("licence_valid").toLocalDate();
                    String passphrase = resultSet.getString("passphrase");
                    return new Agent(id, servId, retired, licence, licenceValid, passphrase);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null; // Return null if no agent is found or an error occurs
    }

    public static void createLoginAttempt(int serviceId, boolean success) {
        String query = "INSERT INTO login_attempts (service_id, success) VALUES (?, ?)";

        try (Connection connection = MySQLConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, serviceId);
            preparedStatement.setBoolean(2, success);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean authenticateLogin(int agentId, String passphrase) {
        Agent agent = readAgentByServiceId(agentId);

        if (agent == null || !passphrase.equals(agent.getPassphrase()) || agent.getRetired()) {
            createLoginAttempt(agentId, false);
            ArrayList<LoginAttempt> lastFailedLogins = getLastLoginAttempts(agentId);
            int consecutiveFails = lastFailedLogins.size() - 1;
            System.out.println(lastFailedLogins.get(consecutiveFails).getLoginStamp().plusMinutes((long) Math.pow(2, consecutiveFails)));
        }

        return agent != null && passphrase.equals(agent.getPassphrase());
    }

    public static ArrayList<LoginAttempt> getLastLoginAttempts(int agentId) {
        ArrayList<LoginAttempt> attempts = new ArrayList<>();

        try (Connection connection = MySQLConnection.connect();
             CallableStatement cs = connection.prepareCall("CALL GetConsecutiveFailedLogins(?)")) {

            cs.setInt(1, agentId);

            try (ResultSet resultSet = cs.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int servId = resultSet.getInt("service_id");  // Assuming the column name is service_id
                    LocalTime loginTime = resultSet.getTime("login_stamp").toLocalTime();
                    LocalDateTime loginStamp = resultSet.getDate("login_stamp").toLocalDate().atTime(loginTime);
                    boolean loginSuccess = resultSet.getBoolean("success");

                    LoginAttempt attempt = new LoginAttempt(id, servId, loginStamp, loginSuccess);
                    attempts.add(attempt);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return attempts;
    }
}
