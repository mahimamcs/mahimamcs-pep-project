package DAO;
import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class RegistrationDAO {

    Connection connection = ConnectionUtil.getConnection();

    public RegistrationDAO(Connection connection) {
        this.connection = connection;
    }

    public void registerUser(Account account) throws SQLException {
        String username = account.getUsername();
        String password = account.getPassword();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM account WHERE username = ?");
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        result.next();
        if (result.getInt(1) > 0) {
            throw new IllegalArgumentException("Username already exists");
        }
        statement = connection.prepareStatement("INSERT INTO account (username, password) VALUES (?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.executeUpdate();
statement = connection.prepareStatement("SELECT account_id FROM account WHERE username = ?");
        statement.setString(1, username);
        result = statement.executeQuery();
        result.next();
        int accountId = result.getInt(1);
        account.setAccount_id(accountId);
    }

}
