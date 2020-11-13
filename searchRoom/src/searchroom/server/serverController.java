/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchroom.server;
import java.io.ByteArrayOutputStream;
import searchroom.models.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cuongnv
 */
public class serverController {
    private int port = 8888;
    private DatagramSocket myServer;
    private Connection con;
    private DatagramPacket receivePacket;
    public serverController() throws IOException, SQLException{
        String dbUrl = "jdbc:mysql://localhost/ltm";
        String classUrl = "com.mysql.jdbc.Driver";
        try {
            Class.forName(classUrl);
            con = DriverManager.getConnection(dbUrl,"root","");
        } catch (Exception ex) {
            //Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        open();
        while(true){
            listening();
        }
    }
    public void open(){
        try {
            myServer= new DatagramSocket(port);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void close(){
        myServer.close();
    }
    public void listening() throws IOException, SQLException{
        String c= receive();
        ArrayList<Room> res = new ArrayList<>();
        res= getSearch(c);
        send(res);
       
    }
    public void send(ArrayList<Room> r) throws IOException{
        byte[] sendData = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(r);
        oos.flush();
        sendData= baos.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());
        myServer.send(sendPacket);
    }
    public String receive(){
        byte[] receiveData = new byte[1024];
        String res= null;
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        try {
            myServer.receive(receivePacket);
            res = new String(receiveData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return res;
    }
    public ArrayList<Room> getSearch(String s) throws SQLException{
        ArrayList<Room> res = new ArrayList<>();
        ResultSet rs= null;
        String query = "select * from tbl_room where name like ? or price=?";
        PreparedStatement ps = con.prepareStatement(query);
        try {
            
            ps.setString(1,"%"+"%");
            ps.setFloat(2,Float.parseFloat(s));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(serverController.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NumberFormatException ex){
            ps.setFloat(2, -1);
        }
        rs=ps.executeQuery();
        while(rs.next()){
                res.add(new Room(rs.getString("name"),rs.getFloat("price")));
        }
        return res;
        
    }
    
}
