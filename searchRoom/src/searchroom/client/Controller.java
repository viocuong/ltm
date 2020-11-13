/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchroom.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import searchroom.models.*;
/**
 *
 * @author cuongnv
 */
public class Controller {
    private String serverHost = "localhost";
    private int serverPort =8888;
    private DatagramSocket myClient;
    private View view;
    public Controller(){
        view = new View();
        view.addListenSearch(new ListenSearch());
        view.setVisible(true);
    }
    public class ListenSearch implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            open();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String c= view.getText();
            System.out.println(c);
            send(c);
            try {
                view.showListRoom(receive());
            } catch (Exception ex) {
                //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                view.show("Khong co phong");
            }
        }
    }
    public void open(){
        try {
            myClient = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send(String s){
        byte[] dataSend = s.getBytes();
        
        try {
            DatagramPacket packetSend = new DatagramPacket(dataSend,dataSend.length, InetAddress.getByName(serverHost),serverPort);
            myClient.send(packetSend);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        myClient.close();
    }
    public ArrayList<Room> receive() throws IOException, ClassNotFoundException{
        ArrayList<Room> res= null;
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
        try {
            myClient.receive(receivePacket);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
        ObjectInputStream ois = new ObjectInputStream(bais);
        res = (ArrayList<Room>)ois.readObject();
        return res;
    }
    
    
}
