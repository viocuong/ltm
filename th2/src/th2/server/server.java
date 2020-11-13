/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th2.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import th2.client.Room;
import th2.client.roomController;

/**
 *
 * @author cuongnv
 */
public class server {
    private Connection con;
    private Room room;
    private ServerSocket myServer;
    public server() throws IOException{
        
        String dbUrl ="jdbc:mysql://localhost:3306/ltm";
        String dbClass = "com.mysql.jdbc.Driver";
        
        try{
            Class.forName(dbClass);
            con = (Connection) DriverManager.getConnection(dbUrl,"root","");
            System.out.println("Connect success !");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        myServer  = new ServerSocket(8001);
        while(true){
            listenning();
        }
    }
    public boolean addRoom(Room room){
        String sql = "insert into tbl_room(name,price) values(?,?)";
        try {
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1,String.valueOf(room.getName()));
            ps.setString(2,String.valueOf(room.getPrice()));
            ps.executeUpdate();
            //form.showMessage("add room success!");
        } catch (Exception ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public void listenning(){
        try {
            Socket clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            Object o = ois.readObject();
            if(o instanceof Room){
                Room room = (Room)o;
                if(addRoom(room)){
                    oos.writeObject("ok");
                }
                else
                    oos.writeObject("false");
            }
            else{
                String ct = (String) o;
                ArrayList<Room> res = getSearchRoom(ct);
                String R="";
                for(Room i : res){
                    R+=("Ten phong: "+i.getName()+" Gia: "+i.getPrice());
                    R+="\n";
                }
                oos.writeObject(R);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Room> getSearchRoom(String ct) throws SQLException{
        String sql = "select * from tbl_room where name=? or price=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, ct);
        ps.setString(2,ct);
        ResultSet rs = ps.executeQuery();
       
        ArrayList<Room> res = new ArrayList<Room>();
        while(rs.next()){
            res.add( new Room(rs.getString(2),rs.getFloat(3)));
        }
        return res;
    }
}
