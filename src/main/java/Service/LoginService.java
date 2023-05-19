package Service;

import DAO.AccountDAO;
import Model.Account;
import java.sql.Connection;

public class LoginService {
    private final AccountDAO accountDAO;

    public LoginService(Connection connection) {
        this.accountDAO = new AccountDAO(connection);
    }

    public Account authenticate(String username, String password) {
        Account account = accountDAO.findByUsernamePassword(username, password);

        if (account != null) {
            return account;
        } else {
            return null;
        }
    }
} 
