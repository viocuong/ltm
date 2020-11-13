/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th2.client;
/**
 *
 * @author cuongnv
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cuongnv
 */
public class roomController{
    private Room room;
    private viewRoom form;
    private Socket mySocket;
    private String serverHost= "localhost";
    private int port= 8001;
    public roomController(){
        form = new viewRoom();
        form.listenBtnAdd((ActionListener) new listenAddRoom());
        form.listentAddSearch(new listenSearch());
        form.setVisible(true);
        
    }

    class listenSearch implements ActionListener {

        public listenSearch() {
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            open();
            String ct = form.getSearch();
            sendContent(ct);
            String result = receive();
            System.out.println(result);
            form.showMessage(result);
            closeConnection();
        }
    }
    class listenAddRoom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            room =form.getRoom();
            //addRoom(room);
            //System.out.println(room.getName()+" "+room.getPrice());
            open();
            sendRoom(room);
            String s = receive();
            System.out.println(s);
            if(s.compareTo("ok") ==0){
                form. showMessage("them thanh cong");
            }
            else form.showMessage("them khong thanh cong");
            closeConnection();
        }
    }
    public void open(){
        try {
            mySocket = new Socket(serverHost,port);
            
        } catch (IOException ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public void sendRoom(Room room){
        try {
            ObjectOutputStream os = new ObjectOutputStream(mySocket.getOutputStream());
            os.writeObject(room);
        } catch (IOException ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public void sendContent(String c){
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(mySocket.getOutputStream());
            os.writeObject(c);
        } catch (Exception ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public String receive(){
        String res = null;
        try {
            ObjectInputStream is = new ObjectInputStream(mySocket.getInputStream());
            Object o=  is.readObject();
            if( o instanceof String){
                res = (String) o;
            }
        } catch (Exception ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return res;
    }
    public ArrayList<Room> receiveSearch(){
        ArrayList<Room> res = null;
        try {
            ObjectInputStream is = new ObjectInputStream(mySocket.getInputStream());
            Object o=  is.readObject();
            if( o instanceof ArrayList){
                res = (ArrayList<Room>) o;
            }
        } catch (Exception ex) {
            //Logger.getLogger(roomController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return res;
    }
    public void closeConnection(){
        try {
            mySocket.close();
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }
    }
    
}

