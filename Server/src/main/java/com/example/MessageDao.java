package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageDao {
    public void create(Connection con, Message msg) throws SQLException {
        try(PreparedStatement pstmt = con.prepareStatement(
                "insert into Messages (id_Account, id_Group, content, time) values (?, ?, ?, ?)")){
            pstmt.setInt(1, msg.getSenderID());
            pstmt.setInt(2, msg.getGroupID());
            pstmt.setString(3, msg.getContent());
            pstmt.setTimestamp(4, new Timestamp(msg.getTime().getTime()));
            pstmt.executeUpdate();
        }
    }
    public void create(Connection con, Integer senderID, Integer groupID, String content, Date time) throws SQLException {
        try(PreparedStatement pstmt = con.prepareStatement(
                "insert into Messages (id_Account, id_Group, content, time) values (?, ?, ?, ?)")){
            pstmt.setInt(1, senderID);
            pstmt.setInt(2, groupID);
            pstmt.setString(3, content);
            pstmt.setTimestamp(4, new Timestamp(time.getTime()));
            pstmt.executeUpdate();
        }
    }

    /**
     * DACA Timpul este specificat , el va trimite toate mesajele mai noi, daca Timoul este NULL, el va trimite TOATE mesajele din acea conversatie.
     * @param con
     * @param groupID
     * @param limit
     * @return O lista cu Toate conversatiile pentru acel grup , ordonate crescator dupa Date
     * @throws SQLException
     */
    public List<Message> findByGroupID(Connection con, Integer groupID, Date limit) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String querry;
        if(limit!=null){
            querry="select * from Messages where id_Group = ? and time > ? ORDER BY time ASC";
        }
        else{
        querry="select * from Messages where id_Group = ? ORDER BY time ASC";
        }
        try(PreparedStatement pstmt = con.prepareStatement(querry)){
            pstmt.setInt(1, groupID);
            if(limit!=null){
                pstmt.setTimestamp(2, new Timestamp(limit.getTime()));
            }
            try(ResultSet rs=pstmt.executeQuery()){
                while (rs.next()){
                    Integer tmpSenderID= rs.getInt("id_Account");
                    Integer tmpGroupID=rs.getInt("id_Group");
                    String tmpContent=rs.getString("content");
                    Timestamp temporary=rs.getTimestamp("time");
                    Date tmpTime=new Date(temporary.getTime());
                    Message tmpMessage= new Message(tmpSenderID, tmpGroupID, tmpContent, tmpTime );
                    messages.add(tmpMessage);
                }
            }
        }
        return messages;
    }
}
