package nu.educom.MI6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Database {
    public static Agent getAgentByServiceId(int serviceId) {
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
}
