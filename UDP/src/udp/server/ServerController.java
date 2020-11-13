/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp.server;
import udp.Model.User;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cuongnv
 */
public class ServerController {
    private int port=8888;
    private Connection con;
    private DatagramPacket receivePacket;
    private DatagramSocket myServer;
    public ServerController() throws SQLException, IOException{
        String dbUrl = "jdbc:mysql://localhost/ltm";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,"root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        open();
        System.out.println("server runing at "+port);
        while(true){
            listening();
        }
    }
    public void open(){
        try {
            this.myServer = new DatagramSocket(port);
            
        } catch (SocketException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        myServer.close();
    }
    public void listening() throws IOException{
        User user = receiveData();
        System.out.println(user.getUserName());
        String res = "false";
        if(checkUser(user)) res="true";
        sendata(res);
    }
    public void sendata(String s) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(s);
        oos.flush();
        
        byte[] data = baos.toByteArray();
        DatagramPacket packetsend = new DatagramPacket(data,data.length,receivePacket.getAddress(),receivePacket.getPort());
        myServer.send(packetsend);
    }
    public User receiveData(){
        User user = null;
        byte[] receiveData = new byte[1024];
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        try {
            myServer.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            user = (User)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    public boolean checkUser(User user){
        
        String query = "select * from tbl_user where username=? and password=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUserName());
            ps.setString(2,user.getPass());
            
            ResultSet rs= ps.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
