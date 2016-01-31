/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.util;

import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author ifnu
 */
public class StringUtils {
    public static String padWithZero(Integer id, Integer digit){
        String strId = id.toString();
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<digit-strId.length();i++){
            buffer.append("0");
        }
        return buffer.append(strId).toString();
    }

    public static String bulanDuaDigit(Date date){
        DateTime dateTime = new DateTime(date.getTime());
        if(dateTime.getMonthOfYear()>=10){
            return String.valueOf(dateTime.getMonthOfYear());
        } else {
            return "0" + dateTime.getMonthOfYear();
        }
    }

}
