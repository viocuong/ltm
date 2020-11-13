/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import models.Room;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class serverController {
    private int port = 8888;
    private Connection con;
    private DatagramSocket myServer;
    private DatagramPacket receivePacket;
    public serverController(){
        
        try {
            String dbUrl = "jdbc:mysql://localhost/ltm";
            String classUrl = "com.mysql.jdbc.Driver";
            Class.forName(classUrl);
            con = DriverManager.getConnection(dbUrl,"root","");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }
        open();
        while(true){
            listening();
        }
        
        
    }
    public void open(){
        try {
            myServer = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        myServer.close();
    }
    public void listening(){
        Room room = receive();
        if(addRoom(room)) send("ok");
        else send("false");
       
    }
    public Room receive(){
        Room res = null;
        try {
            
            byte[] receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData,receiveData.length);
            myServer.receive(receivePacket);
            
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            res = (Room) ois.readObject();
            
            
        } catch (IOException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
        
    }
    public boolean addRoom(Room room){
        String query = "insert into tbl_room(name,price) values(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, room.getName());
            ps.setFloat(2, room.getPrice());
            ps.executeUpdate();
        } catch (SQLException ex) {
            return false;
            //Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    public void send(String data){
        byte[] datasend = data.getBytes();
        
        
        DatagramPacket packetSend = new DatagramPacket(datasend,datasend.length,receivePacket.getAddress(),receivePacket.getPort());
        try {
            myServer.send(packetSend);
        } catch (IOException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
