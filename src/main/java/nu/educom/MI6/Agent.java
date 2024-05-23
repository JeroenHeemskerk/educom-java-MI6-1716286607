package nu.educom.MI6;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Agent {
    public static boolean getLicenceByServiceId(int serviceId) {
        String query = "SELECT licence_to_kill FROM agents WHERE service_id = ?";
        try (var connection =  MySQLConnection.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the query
            preparedStatement.setInt(1, serviceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean("licence_to_kill");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            // onzeker over welke default ik moet returnen in geval van error
            return false;
        }
    }

    public static java.sql.Date getLicenceDateByServiceId(int serviceId) {
        String query = "SELECT licence_valid FROM agents WHERE service_id = ?";
        try (var connection =  MySQLConnection.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the query
            preparedStatement.setInt(1, serviceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getDate("licence_valid");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            // onzeker over welke default ik moet returnen in geval van error
            return null;
        }
    }

    public static boolean getRetiredByServiceId(int serviceId) {
        String query = "SELECT retired FROM agents WHERE service_id = ?";
        try (var connection =  MySQLConnection.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the query
            preparedStatement.setInt(1, serviceId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean("retired");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            // onzeker over welke default ik moet returnen in geval van error
            return false;
        }
    }
}
