package Service;

import DAO.MessageDAO;
import Model.Message;
import java.sql.Connection;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(Connection connection) {
        this.messageDAO = new MessageDAO(connection);
    }
    public Message NewMessage(Message message) {
        return messageDAO.createMessage(message);
    }
    public Message GetMessageByID(Integer message_id) {
        return messageDAO.getMessage(message_id);
    }
    public List<Message> GetAllMessages() {
        return messageDAO.getAllMessages();
    }
    public List<Message> GetAllMessagesByUser(Integer account_id) {
        return messageDAO.getAllMessagesByUser(account_id);
    }
    public boolean UpdateMessageText(int messageId, String newMessageText) {
        Message existingMessage = messageDAO.getMessage(messageId);
        if (existingMessage != null) {
            existingMessage.setMessage_text(newMessageText);
            return messageDAO.updateMessageText(messageId, existingMessage.getMessage_text());
        }
        else {
            return false;
        }
    }
    public Message DeleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }
}
