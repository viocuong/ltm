/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class threadLog extends Thread{
    private String fileName ;
    private long time ;
    private String nameThead ;
    public threadLog(String nameThread , String file, long time){
        this.nameThead= nameThread;
        this.fileName = file;
        this.time = time ;
    }
    
    public void run() {
        //super.run(); //To change body of generated methods, choose Tools | Templates.
        for(int i=0 ;i<10;i++){
            try {
                Writer w = new BufferedWriter(new FileWriter(fileName,true));
                sleep(time);
                w.append(getName() + "[ "+Calendar.getInstance().getTime() + "] :"+i+"\n");
                w.close();
            } catch (Exception ex) {
                Logger.getLogger(threadLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
