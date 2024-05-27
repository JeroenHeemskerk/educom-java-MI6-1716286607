package nu.educom.MI6;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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

    public static ArrayList<LoginAttempt> readLastLoginAttempts(int agentId) {
        ArrayList<LoginAttempt> attempts = new ArrayList<>();

        try (Connection connection = MySQLConnection.connect();
             PreparedStatement cs = connection.prepareStatement("SELECT * FROM login_attempts WHERE service_id = ? AND id > IFNULL((SELECT MAX(id) FROM login_attempts WHERE success = 1 AND service_id = ?), 0)")) {

            cs.setInt(1, agentId);
            cs.setInt(2, agentId);

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

    public static LoginAttempt readLastLoginAttempt(int agentId) {
        String query = "SELECT * FROM login_attempts WHERE service_id = ? ORDER BY id DESC LIMIT 1";

        try (Connection connection = MySQLConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, agentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int servId = resultSet.getInt("service_id");  // Assuming the column name is service_id
                    LocalTime loginTime = resultSet.getTime("login_stamp").toLocalTime();
                    LocalDateTime loginStamp = resultSet.getDate("login_stamp").toLocalDate().atTime(loginTime);
                    boolean loginSuccess = resultSet.getBoolean("success");

                    return new LoginAttempt(id, servId, loginStamp, loginSuccess);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null; // Return null if no agent is found or an error occurs
    }
}
