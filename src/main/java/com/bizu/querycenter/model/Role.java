package com.bizu.querycenter.model;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    private Role(String name){
        this.name = name;
    }

    public String getRole(){
        return name;
    }
}
