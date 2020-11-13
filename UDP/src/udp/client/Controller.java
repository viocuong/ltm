/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package udp.client;
import udp.Model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class Controller {
    private User user;
    private formLogin form;
    private String serverHost= "localhost";
    private int serverPort = 8888;
    private int clientPort = 6666;
    private DatagramSocket myclient; 
    public Controller(){
        form = new formLogin();
        form.addListenLogin(new classListener());
        form.setVisible(true);
    }
    public class classListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                user = form.getUser();
                openConnect();
                
                send(user);
                receive();
                close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    public void openConnect(){
        try {
            this.myclient = new DatagramSocket(clientPort);
        } catch (SocketException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void close(){
        this.myclient.close();
    }
    public void send(User user) throws IOException{
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            oos.flush();
            byte[] sendata = baos.toByteArray();
            DatagramPacket datapacket = new DatagramPacket(sendata,sendata.length, InetAddress.getByName(serverHost),serverPort);
            System.out.println(InetAddress.getByName(serverHost));
            myclient.send(datapacket);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void receive() throws ClassNotFoundException{
        byte[] dataReceive= new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(dataReceive, dataReceive.length);
        try {
            myclient.receive(receivePacket);
            ByteArrayInputStream bais = new ByteArrayInputStream(dataReceive);
            ObjectInputStream ois = new ObjectInputStream(bais);
            String r = (String)ois.readObject();
            form.showMasage(r);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    
    
}
