/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artivisi.pos.service.impl;

import com.artivisi.pos.dao.master.ProdukDao;
import com.artivisi.pos.dao.master.RunningNumberDao;
import com.artivisi.pos.dao.master.SystemPropertyDao;
import com.artivisi.pos.dao.transaksi.PembayaranDao;
import com.artivisi.pos.dao.transaksi.PembelianDao;
import com.artivisi.pos.dao.transaksi.PenjualanDao;
import com.artivisi.pos.dao.transaksi.SaldoStokDao;
import com.artivisi.pos.dao.transaksi.SesiKassaDao;
import com.artivisi.pos.model.master.Produk;
import com.artivisi.pos.model.master.constant.TransaksiRunningNumberEnum;
import com.artivisi.pos.model.transaksi.Pembayaran;
import com.artivisi.pos.model.transaksi.Pembelian;
import com.artivisi.pos.model.transaksi.PembelianDetail;
import com.artivisi.pos.model.transaksi.Penjualan;
import com.artivisi.pos.model.transaksi.PenjualanDetail;
import com.artivisi.pos.model.transaksi.SaldoStok;
import com.artivisi.pos.model.transaksi.SesiKassa;
import com.artivisi.pos.model.transaksi.constant.JenisPembayaran;
import com.artivisi.pos.service.TransaksiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author endy
 */
@Service("transaksiService")
@Transactional(readOnly=true)
public class TransaksiServiceImpl implements TransaksiService{
    @Autowired private PenjualanDao penjualanDao;
    @Autowired private PembelianDao pembelianDao;
    @Autowired private SaldoStokDao saldoStokDao;
    @Autowired private RunningNumberDao runningNumberDao;
    @Autowired private ProdukDao produkDao;
    @Autowired private SesiKassaDao sesiKassaDao;
    @Autowired private PembayaranDao pembayaranDao;
    @Autowired private SystemPropertyDao systemPropertyDao;

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Penjualan penjualan) {
        if(penjualan.getId() ==null){//insert penjualan
            penjualan.setId(runningNumberDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PENJUALAN));
            int i=1;
            for(PenjualanDetail detail : penjualan.getDetails()){
                detail.setId(penjualan.getId() + i++);
                Produk p = produkDao.cariBerdasarId(detail.getProduk().getId());
                p.setStok(p.getStok() - detail.getKuantitas());
                produkDao.simpan(p);
            }
            //pembayaran
            for(Pembayaran p : penjualan.getPembayarans()){
                p.setId(runningNumberDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PEMBAYARAN));
            }
            updateSaldoStok(penjualan);
            penjualanDao.simpan(penjualan);
        }
        //penjualan juga tidak bisa diupdate, bisanya diretur
    }

    private void updateSaldoStok(Penjualan penjualan){
        for(PenjualanDetail d : penjualan.getDetails()){
            SaldoStok saldoStok = saldoStokDao.cari(d.getProduk());
            saldoStok.setBeli(saldoStok.getJual() + d.getKuantitas());
            saldoStokDao.simpan(saldoStok);
        }
    }


    public Penjualan cariPenjualan(String id) {
        return penjualanDao.cariBerdasarId(id);
    }

    public List<Penjualan> semuaPenjualan() {
        return penjualanDao.semua();
    }

    @Transactional(isolation=Isolation.SERIALIZABLE)
    public void simpan(Pembelian pembelian) {
        if(pembelian.getId() == null){ //insert pembelian
            pembelian.setId(runningNumberDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.PEMBELIAN));
            int i = 1;
            for(PembelianDetail detail : pembelian.getDetails()){
                detail.setId(pembelian.getId() + i++);
            }
            for(PembelianDetail d : pembelian.getDetails()){
                Produk p = produkDao.cariBerdasarId(d.getProduk().getId());
                p.setStok(p.getStok() + d.getKuantitas());
                p.setHargaPokok(d.getHarga());
                produkDao.simpan(p);
            }
            updateSaldoStok(pembelian);
            pembelianDao.simpan(pembelian);
        }
        //pembelian ga bisa diupdate, bisanya lewat retur
    }

    private void updateSaldoStok(Pembelian pembelian){
        for(PembelianDetail d : pembelian.getDetails()){
            SaldoStok saldoStok = saldoStokDao.cari(d.getProduk());
            saldoStok.setBeli(saldoStok.getBeli() + d.getKuantitas());
            saldoStokDao.simpan(saldoStok);
        }
    }

    public Pembelian cariPembelian(String id) {
        return pembelianDao.cariBerdasarId(id);
    }

    public List<Pembelian> semuaPembelian() {
        return pembelianDao.semua();
    }
    
    @Transactional
    public void hapus(SesiKassa s){
        sesiKassaDao.hapus(s);
    }

    @Transactional
    public void simpan(SesiKassa s){
        s.setId(runningNumberDao.ambilBerikutnyaDanSimpan(TransaksiRunningNumberEnum.SESI_KASSA));
        sesiKassaDao.hapus(s);
    }

    public SesiKassa cariSesiKassa(String id){
        return sesiKassaDao.cariBardasarId(id);
    }

    public List<SesiKassa> semuaSesiKassa(){
        return sesiKassaDao.semua();
    }

    public Pembayaran cariPembayaran(String id){
        return pembayaranDao.cariBerdasarId(id);
    }

    public List<Pembayaran> cariPembayaran(SesiKassa sesiKassa, JenisPembayaran jenis){
        return pembayaranDao.cariBerdasarJenisDanSesi(sesiKassa, jenis);
    }

}
