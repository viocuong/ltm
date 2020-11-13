/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import models.Room;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class Controller {
    private View view;
    private int serverPort=8888;
    private String serverHost= "localhost";
    private DatagramSocket myClient;
    public Controller(){
        view = new View();
        view.addListenAddRoom(new classListenAddRoom());
        view.setVisible(true);
        
    }
    public class classListenAddRoom implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            open();
            send(view.getRoom());
            receive();
            close();
            
            
        }
        
    }
    public void open() {
        try {
            myClient= new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        myClient.close();
    }
    public void send(Room room){
        try {
            byte[] datasend= new byte[1024];
            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            ObjectOutputStream oos= new ObjectOutputStream(bais);
            oos.writeObject(room);
            oos.flush();
            datasend=bais.toByteArray();
            InetAddress inet  = InetAddress.getByName(serverHost);
            DatagramPacket packetSend = new DatagramPacket(datasend,datasend.length,inet,serverPort);
            myClient.send(packetSend);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void receive(){
        Room room=null;
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            myClient.receive(receivePacket);
            
            
            
            String o = new String(receiveData);
            
            view.showMessage((String)o);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
