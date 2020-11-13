/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class controller {
    private User user;
    private loginView view ;
    private Connection con;

    public controller() throws SQLException{
        String dbUrl = "jdbc:mysql://localhost:3306/th1";
        String dbClass = "com.mysql.jdbc.Driver";
        
        try {
            Class.forName(dbClass);
            con = (Connection) DriverManager.getConnection(dbUrl,"root","");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        view = new loginView();
        view.setVisible(true);
        view.addViewtBtn(new ActionBtn());
        view.addListenerBtnViewUser(new ActionBtnView());
            
    }

    private class ActionBtnView implements ActionListener {

        public ActionBtnView() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String query = "select * from tbl_user";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                String ansString="";
                while(rs.next()){
                    ansString+="(";
                    ansString+=rs.getString(1);
                    ansString+=", ";
                    ansString+=rs.getString("userName");
                    ansString+=", ";
                    ansString+=rs.getString("passWord");
                    ansString+=") ";
                    
                    
                }
                view.showMassage(ansString);
            } catch (SQLException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    class ActionBtn implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                user = view.getUser();
                String query = "select * from tbl_user where userName= ? and passWord=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getPassWord());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) view.showMassage("dang nhap thanh cong");
                else view.showMassage("dang nhap that bai");
            } catch (SQLException ex) {
                Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }
    
}
