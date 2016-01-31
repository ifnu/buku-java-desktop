/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.projecttemplate.pos.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ifnu
 */
public class MD5FileHashUtil {

    private static final Log LOGGER = LogFactory.getLog(MD5FileHashUtil.class);

    public static String createChecksum(byte[] file)  {
        try {
            MessageDigest complete = MessageDigest.getInstance("MD5");
            complete.update(file, 0, file.length);
            return getHexString(complete.digest());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return "";
    }
    static final byte[] HEX_CHAR_TABLE = {
        (byte) '0', (byte) '1', (byte) '2', (byte) '3',
        (byte) '4', (byte) '5', (byte) '6', (byte) '7',
        (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
        (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
    };

    private static String getHexString(byte[] raw)
            throws UnsupportedEncodingException {
        byte[] hex = new byte[2 * raw.length];
        int index = 0;
        for (byte b : raw) {
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex, "ASCII");
    }

}
