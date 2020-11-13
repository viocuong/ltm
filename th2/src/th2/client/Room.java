/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th2.client;

import java.io.Serializable;

/**
 *
 * @author cuongnv
 */
public class Room implements Serializable{
    //private int id;
    private String name;
    private float price;

    
    public Room(){}
    public Room( String name, float price) {
        //this.id = id;
        this.name = name;
        this.price = price;
    }

    

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
}