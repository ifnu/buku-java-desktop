/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artivisi.pos.model.master.constant;

/**
 *
 * @author ifnu
 */
public enum TransaksiRunningNumberEnum {

    PEMBELIAN("ID_BELI", "BLI", 5),
    PENJUALAN("ID_JUAL", "JLA", 5),
    PEMBAYARAN("ID_PEMBAYARAN", "BYR", 5),
    SESI_KASSA("ID_SESI", "SES", 5),
    SALDO_STOK("ID_SALDO_STOK", "SAL", 5);

    private String id;
    private String prefix;
    private Integer digit;

    private TransaksiRunningNumberEnum(String id, String prefix, Integer digit) {
        this.id = id;
        this.prefix = prefix;
        this.digit = digit;
    }

    public Integer getDigit() {
        return digit;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }
}
