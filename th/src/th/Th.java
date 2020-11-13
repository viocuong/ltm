/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th;

/**
 *
 * @author cuongnv
 */
public class Th {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        threadLog l1 = new threadLog("t1","out.txt", 1000);
        threadLog l2 = new threadLog("t2","out.txt", 500);
        l1.start();
        l2.start();
    }
    
}
