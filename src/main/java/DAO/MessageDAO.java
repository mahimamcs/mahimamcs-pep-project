package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            return new Message(generated_message_id, message.getMessage_text(), generated_message_id);
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
}
