package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;

public class MessageDAO {
    
    private final Connection connection;

    public MessageDAO(Connection connection) {
        this.connection = connection;
    }

    public Message createMessage(Message message) {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try 
        {PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, message.getPosted_by());
        statement.setString(2, message.getMessage_text());
        statement.setLong(3, message.getTime_posted_epoch());
        statement.executeUpdate();
        ResultSet pkeyResultSet = statement.getGeneratedKeys();
        if(pkeyResultSet.next()){
            int generated_message_id = (int) pkeyResultSet.getLong(1);
            return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessage(Integer message_id) {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, message_id);
            ResultSet result = statement.executeQuery();
            if (result.next()){ 
                return new Message(message_id, result.getInt("posted_by"), result.getString("message_text"),result.getLong("time_posted_epoch"));
            }
            else {
                return null;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Message message = new Message(result.getInt("message_id"),
                result.getInt("posted_by"),
                result.getString("message_text"),
                result.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public List<Message> getAllMessagesByUser(int account_id){
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE posted_by = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Message message = new Message(result.getInt("message_id"),
                result.getInt("posted_by"),
                result.getString("message_text"),
                result.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public boolean updateMessageText(int messageId, String newMessageText) {
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newMessageText);
            statement.setInt(2, messageId);
            int rowsUpdated = statement.executeUpdate();
    
            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Message deleteMessage(int messageId) {
        Message message = getMessage(messageId); 
        String sql = "DELETE message WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, messageId);
            int rowsDeleted = statement.executeUpdate();
    
            if (rowsDeleted > 0) {
                return message;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
    
