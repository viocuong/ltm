/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class Client {
    private Socket mysocket;
    private Scanner in;
    private String host = "localhost";
    private int port=8888;
    public Client() throws ClassNotFoundException{
        open();
        //proceed();
        proceed1();
        close();
    }
    public void open(){
        try {
            mysocket= new Socket(host,port);
            
        } catch (Exception ex) {
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    void proceed() throws ClassNotFoundException{
        in= new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        String s = "" +a +" "+ b;
        try {
            
            DataInputStream is = new DataInputStream(mysocket.getInputStream());
            DataOutputStream os = new DataOutputStream(mysocket.getOutputStream());
            os.writeUTF("add");
            os.writeInt(a);
            os.writeInt(b);
            String r= is.readUTF();
            int result = is.readInt();
            System.out.println(r);
            System.out.println(result);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void proceed1(){
        in = new Scanner(System.in);
        try {
            DataOutputStream os = new DataOutputStream(mysocket.getOutputStream());
            DataInputStream is = new DataInputStream(mysocket.getInputStream());
            for(int i=2; i<=10;i++){
                int x =in.nextInt();
                os.writeInt(x);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void close(){
        try {
            mysocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
           
}
