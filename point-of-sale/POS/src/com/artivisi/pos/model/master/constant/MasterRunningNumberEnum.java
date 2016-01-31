/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artivisi.pos.model.master.constant;

/**
 *
 * @author ifnu
 */
public enum MasterRunningNumberEnum {

    USER("SEC_USER", "USR", 7);
    private String id;
    private String prefix;
    private Integer digit;

    private MasterRunningNumberEnum(String name, String prefix, Integer digit) {
        this.id = name;
        this.prefix = prefix;
        this.digit = digit;
    }

    public String getId() {
        return id;
    }

    public Integer getDigit() {
        return digit;
    }

    public String getPrefix() {
        return prefix;
    }
}
