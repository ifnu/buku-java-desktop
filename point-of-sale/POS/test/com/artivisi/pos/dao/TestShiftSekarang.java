/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.dao;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ifnu
 */
public class TestShiftSekarang {

    public TestShiftSekarang() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testShiftSekarang(){
        DateTime jamShiftSiangMulai = new DateTime(2009, 8, 1, 9, 0, 0, 0);
        DateTime jamShiftSiangSelesai = new DateTime(2009, 8, 1, 16, 0, 0, 0);

        DateTime jamShiftMalamMulai = new DateTime(2009, 8, 1, 16, 0, 0, 0);
        DateTime jamShiftMalamSelesai = new DateTime(2009, 8, 1, 23, 0, 0, 0);

        assertTrue(jamShiftSiangMulai.getMinuteOfDay() < new DateTime(2009,8,9,11,0,0,0).getMinuteOfDay()
                 && jamShiftSiangSelesai.getMinuteOfDay() > new DateTime(2009,8,9,11,0,0,0).getMinuteOfDay());

    }

}