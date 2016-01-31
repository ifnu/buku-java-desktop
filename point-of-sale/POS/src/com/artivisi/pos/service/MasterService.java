
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.service;

import com.artivisi.pos.model.master.Cabang;
import com.artivisi.pos.model.master.KartuPembayaran;
import com.artivisi.pos.model.master.Kassa;
import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.PulsaElektrik;
import com.artivisi.pos.model.master.RunningNumber;
import com.artivisi.pos.model.master.Satuan;
import com.artivisi.pos.model.master.Shift;
import com.artivisi.pos.model.master.SystemProperty;
import com.artivisi.pos.model.master.constant.MasterRunningNumberEnum;
import com.artivisi.pos.model.master.constant.TransaksiRunningNumberEnum;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ifnu
 */
public interface MasterService {
    public void hapus(Produk p);
    public void simpan(Produk p);
    public void simpan(Produk p,Long saldoAwal);
    public List<Produk> semuaProduk();
    public List<Produk> semuaProdukNonPulsaElektrik();
    public Produk produkBerdasarId(String id);

    public void hapus(PulsaElektrik p);
    public void simpan(PulsaElektrik p);
    public List<PulsaElektrik> semuaPulsaElektrik();
    public PulsaElektrik pulsaElektrikBerdasarId(String id);

    public void hapus(Cabang p);
    public void simpan(Cabang p);
    public List<Cabang> semuaCabang();
    public Cabang cabangBerdasarId(String id);

    public void hapus(Satuan s);
    public void simpan(Satuan s);
    public List<Satuan> semuaSatuan();
    public Satuan satuanBerdasarId(String id);

    public void hapus(Kassa k);
    public Boolean simpan(Kassa k);
    public List<Kassa> semuaKassa();
    public Kassa kassaBerdasarId(String id);

    public void hapus(Shift s);
    public void simpan(Shift s);
    public List<Shift> semuaShift();
    public Shift shiftBerdasarId(String id);
    public Shift shiftSekarang();

    public void hapus(KartuPembayaran k);
    public void simpan(KartuPembayaran k);
    public List<KartuPembayaran> semuaKartuPembayaran();
    public KartuPembayaran kartuPembayaranBerdasarId(String id);

    public void simpan(RunningNumber p);
    public List<RunningNumber> semuaRunningNumber();
    public String ambilBerikutnya(MasterRunningNumberEnum id, String idCabang);
    public String ambilBerikutnya(TransaksiRunningNumberEnum id,Date date,String idCabang);
    public String ambilBerikutnya(TransaksiRunningNumberEnum id);
    public Date tanggalKerja();

    public void simpan(SystemProperty p);
    public List<SystemProperty> semuaSystemProperty();
    public SystemProperty systemPropertyBerdasarId(String id);

    public Date tanggalServer();
}
