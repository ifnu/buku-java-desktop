/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.service;

import com.artivisi.pos.model.transaksi.Pembayaran;
import com.artivisi.pos.model.transaksi.Pembelian;
import com.artivisi.pos.model.transaksi.Penjualan;
import com.artivisi.pos.model.transaksi.SesiKassa;
import com.artivisi.pos.model.transaksi.constant.JenisPembayaran;
import java.util.List;

/**
 *
 * @author endy
 */
public interface TransaksiService {

    public void simpan(Penjualan p);
    public Penjualan cariPenjualan(String id);
    public List<Penjualan> semuaPenjualan();

    public void simpan(Pembelian p);
    public Pembelian cariPembelian(String id);
    public List<Pembelian> semuaPembelian();

    public void hapus(SesiKassa s);
    public void simpan(SesiKassa p);
    public SesiKassa cariSesiKassa(String id);
    public List<SesiKassa> semuaSesiKassa();

    public Pembayaran cariPembayaran(String id);
    public List<Pembayaran> cariPembayaran(SesiKassa sesiKassa, JenisPembayaran jenis);

}
