package Service;

import java.sql.Connection;
import java.sql.SQLException;
//import java.util.List;

import DAO.RegistrationDAO;
import Model.Account;


public class RegistrationService {

    private RegistrationDAO registrationDAO;

    public RegistrationService(Connection connection) {
        this.registrationDAO = new RegistrationDAO(connection);
    }

    public Account registerAccount(Account account) throws SQLException {
        registrationDAO.registerUser(account);
        return account;
    }
}
