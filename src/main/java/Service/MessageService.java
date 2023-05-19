package Service;

import DAO.MessageDAO;
import Model.Message;
import java.sql.Connection;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(Connection connection) {
        this.messageDAO = new MessageDAO(connection);
    }
    public Message NewMessage(Message message) {
        return messageDAO.createMessage(message);
    }
}