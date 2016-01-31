package com.googlecode.projecttemplate.pos.model;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author ifnu
 */
public enum MaritalStatus implements Serializable{

    SINGLE("single"),MARRIED("menikah"),DIVORCED("cerai");

    private MaritalStatus(String text) {
        this.text = text;
    }

    private String text;

    @Override
    public String toString() {
        return text;
    }


}
