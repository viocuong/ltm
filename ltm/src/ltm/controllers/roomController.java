/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm.controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ltm.models.*;
import ltm.views.*;
/**
 *
 * @author cuongnv
 */
public class roomController{
    private Connection con;
    private Room room;
    private roomView form;
    public roomController(){
        String dbUrl ="jdbc:mysql://localhost:3306/ltm";
        String dbClass = "com.mysql.jdbc.Driver";
        try{
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,"root","");
            System.out.println("Connect success !");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        form = new roomView();
        form.listenBtnAdd((ActionListener) new listenAddRoom());
        form.setVisible(true);
        
    }
    class listenAddRoom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            room =form.getRoom();
            addRoom(room);
        }
    }
    public void addRoom(Room room){
        String sql = "insert into tbl_room(name,price) values(?,?)";
        try {
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1,String.valueOf(room.getName()));
            ps.setString(2,String.valueOf(room.getPrice()));
            ps.executeUpdate();
            form.showMessage("add room success!");
        } catch (SQLException ex) {
            Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
