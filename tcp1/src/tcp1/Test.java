/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cuongnv
 */
public class Test {
    
    public Test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
        
        
    }
    void pro() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        try {
            
            Method m= getClass().getMethod("show",int.class);
            
            int x=(int)m.invoke(new Test(),1);
            System.out.println(x);
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int show(int a){
        return a;
    }
    
}
