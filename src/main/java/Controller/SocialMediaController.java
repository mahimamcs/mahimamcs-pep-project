package Controller;

//import java.sql.Connection;
import java.sql.SQLException;

import Model.Account;
import Model.Message;
import Service.RegistrationService;
import Service.*;
import Util.ConnectionUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messagesHandler);
        app.get("/messages/{message_id}", this::getmessagesHandler);
       
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context context) throws SQLException {
        try {        
            Account account = context.bodyAsClass(Account.class);        
            String username = account.getUsername();
            String password = account.getPassword();
            if (username.isBlank() || password.length() < 4) {
                context.status(400);
                return;
            }
    
            
            RegistrationService registrationService = new RegistrationService(ConnectionUtil.getConnection());
            Account registeredAccount = registrationService.registerAccount(account);
    
            
            context.status(200).json(registeredAccount);
        } catch (SQLException e) {
            context.status(500).json("Failed to register account: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            context.result("").status(400);
        
    }
    }
    public void loginHandler (Context context) throws SQLException {
        try {          
        Account account = context.bodyAsClass(Account.class);
        LoginService loginService = new LoginService(ConnectionUtil.getConnection());
        Account authenticatedAccount = loginService.authenticate(account.getUsername(), account.getPassword());
        
        if (authenticatedAccount != null) {
            context.status(200).json(authenticatedAccount);
        } else {
            context.status(401);
        }
        } catch (IllegalArgumentException e) {
            context.status(400);
        }
    }

    public void messagesHandler (Context context) throws SQLException {
        try {          
            Message message = context.bodyAsClass(Message.class);
            if (message.getMessage_text().length() > 254) {
                context.status(400);
            }
            else if (message.getMessage_text() == "") { 
                context.status(400);
            }
            else {
                MessageService messageService = new MessageService(ConnectionUtil.getConnection());
                Message newMesage = messageService.NewMessage(message);
        
                if (newMesage != null) {
                    context.status(200).json(newMesage);
                } else {
                    context.status(400);
                }
            }
        } catch (IllegalArgumentException e) {
            context.status(400);
        }
    }
}

