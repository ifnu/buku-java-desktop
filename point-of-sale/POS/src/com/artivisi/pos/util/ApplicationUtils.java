/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artivisi.pos.util;

import com.artivisi.pos.model.master.Kassa;
import com.artivisi.pos.ui.frame.FrameUtama;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author ifnu
 */
public class ApplicationUtils {

    public static boolean cekKassaSetting() {
        try {
            String userHome = System.getProperty("user.home");
            File posDir = new File(userHome + File.separatorChar + ".pos");
            if (!posDir.exists()) {
                return false;
            }
            File kassaFile = new File(userHome + File.separatorChar + ".pos" + File.separatorChar + "kassa.properties");
            if (!kassaFile.exists()) {
                return false;
            }
            //cek id kassa
            Properties p = new Properties();
            p.load(new FileReader(kassaFile));
            String idKassa = p.getProperty("id_kassa");
            String namaKassa = p.getProperty("nama_kassa");
            String deskripsi = p.getProperty("deskripsi_kassa");
            //TODO dapetin IP gimana?
            Kassa kassa = FrameUtama.getMasterService().kassaBerdasarId(idKassa);
            if(kassa == null){
                kassa = new Kassa();
                kassa.setDeskripsi(deskripsi);
                kassa.setId(idKassa);
                kassa.setNama(namaKassa);
                Boolean ret = FrameUtama.getMasterService().simpan(kassa);
                return ret;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Kassa kassaSetting(){
        try {
            String userHome = System.getProperty("user.home");
            File posDir = new File(userHome + File.separatorChar + ".pos");
            if (!posDir.exists()) {
                return null;
            }
            File kassaFile = new File(userHome + File.separatorChar + ".pos" + File.separatorChar + "kassa.properties");
            if (!kassaFile.exists()) {
                return null;
            }
            //cek id kassa
            Properties p = new Properties();
            p.load(new FileReader(kassaFile));
            String idKassa = p.getProperty("id_kassa");
            String namaKassa = p.getProperty("nama_kassa");
            String deskripsi = p.getProperty("deskripsi_kassa");
            //TODO dapetin IP gimana?
            Kassa kassa = new Kassa();
            kassa.setDeskripsi(deskripsi);
            kassa.setId(idKassa);
            kassa.setNama(namaKassa);
            return kassa;
        } catch (IOException ex) {
            return null;
        }
    }

}
