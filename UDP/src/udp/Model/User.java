/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp.Model;

import java.io.Serializable;

/**
 *
 * @author cuongnv
 */
public class User implements Serializable{
    private String userName;
    private String pass;
    public User(String u, String p){
        this.userName= u;
        this.pass = p;
    }
    public String getUserName() {
        return userName;
    }
    public String getPass() {
        return pass;
    }
}
