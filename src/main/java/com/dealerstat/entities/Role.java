package com.dealerstat.entities;

import java.io.Serializable;

public enum Role implements Serializable {
    Admin("Admin"),
    Dealer("Dealer");
    private final String name;

    Role(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
