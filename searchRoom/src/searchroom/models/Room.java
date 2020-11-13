/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchroom.models;

import java.io.Serializable;

/**
 *
 * @author cuongnv
 */
public class Room implements Serializable{
    private String name;
    private float price;
    public Room(String n, float p){
        this.name = n;
        this.price= p;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
  
}
