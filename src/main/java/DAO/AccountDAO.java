package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;

public class AccountDAO {

    private final Connection connection;

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public Account findByUsernamePassword(String username, String password) {
        String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            result.next();
            if (result.getInt("account_id") > 0) {
                return new Account(result.getInt("account_id"), username, password);
            }
            else {
                throw new IllegalArgumentException("Invalid User Name or password");
            }
        } catch (SQLException e) {           
        }
        return null;
    }
}