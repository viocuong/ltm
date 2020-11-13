/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class Server {
    private ServerSocket server;
   // private Socket clientSocket;
    
    public Server(){
        try {
            server = new ServerSocket(8888);
            System.out.println("server listening");
            while(true){
                listening();
            }
        } catch (Exception ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    void listening() throws ClassNotFoundException, NoSuchMethodException{
        try {
            Socket clientSocket = server.accept();
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
//            String c =(String) is.readUTF();
//            
//            
//            int a = is.readInt();
//            int b= is.readInt();
//            os.writeUTF(c);
//            
//            Method method = getClass().getMethod(c,int.class,int.class);
//            try {
//                os.writeInt((int) method.invoke(new Server(), a,b));
//            } catch (IllegalAccessException ex) {
//                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IllegalArgumentException ex) {
//                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InvocationTargetException ex) {
//                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            System.out.println(c);
            while(true){
                int receive = is.readInt();
                System.out.println(receive);
                if(receive == 0 )break;
            }
           
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int add(int a,int b){
        a=a+b;
        
        return a;
        
    }
}
