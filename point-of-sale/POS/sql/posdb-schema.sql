-- MySQL dump 10.11
--
-- Host: localhost    Database: posdb
-- ------------------------------------------------------
-- Server version	5.0.67

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `MST_CABANG`
--

DROP TABLE IF EXISTS `MST_CABANG`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_CABANG` (
  `ID_CABANG` varchar(3) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY  (`ID_CABANG`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_KARTU_BAYAR`
--

DROP TABLE IF EXISTS `MST_KARTU_BAYAR`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_KARTU_BAYAR` (
  `ID_KARTU` varchar(10) NOT NULL,
  `BANK` varchar(50) default NULL,
  `NAMA` varchar(35) default NULL,
  PRIMARY KEY  (`ID_KARTU`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_KASSA`
--

DROP TABLE IF EXISTS `MST_KASSA`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_KASSA` (
  `ID_KASSA` varchar(10) NOT NULL,
  `DESKRIPSI` varchar(255) default NULL,
  `LOCAL_IP` varchar(20) default NULL,
  `NAMA` varchar(25) NOT NULL,
  PRIMARY KEY  (`ID_KASSA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_PRODUK`
--

DROP TABLE IF EXISTS `MST_PRODUK`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_PRODUK` (
  `ID_PRODUK` varchar(30) NOT NULL,
  `HARGA_JUAL` decimal(19,2) default NULL,
  `HARGA_POKOK` decimal(19,2) default NULL,
  `NAMA` varchar(90) default NULL,
  `PULSA_ELEKTRIK` bit(1) NOT NULL,
  `STOK` int(11) NOT NULL,
  `ID_SATUAN` varchar(10) default NULL,
  PRIMARY KEY  (`ID_PRODUK`),
  KEY `FKD0C5E61EBFC88137` (`ID_SATUAN`),
  CONSTRAINT `FKD0C5E61EBFC88137` FOREIGN KEY (`ID_SATUAN`) REFERENCES `mst_satuan` (`ID_SATUAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_PRODUK_SATUAN`
--

DROP TABLE IF EXISTS `MST_PRODUK_SATUAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_PRODUK_SATUAN` (
  `ID_PRODUK_SATUAN` varchar(60) NOT NULL,
  `KUANTITAS_KEMASAN` bigint(20) default NULL,
  `ID_PRODUK` varchar(30) NOT NULL,
  PRIMARY KEY  (`ID_PRODUK_SATUAN`),
  KEY `FK651C42FDB7658619` (`ID_PRODUK`),
  CONSTRAINT `FK651C42FDB7658619` FOREIGN KEY (`ID_PRODUK`) REFERENCES `mst_produk` (`ID_PRODUK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_PULSA_ELEKTRIK`
--

DROP TABLE IF EXISTS `MST_PULSA_ELEKTRIK`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_PULSA_ELEKTRIK` (
  `ID_PULSA` varchar(30) NOT NULL,
  `HARGA_JUAL` decimal(19,2) NOT NULL,
  `NAMA` varchar(90) NOT NULL,
  `NOMINAL` decimal(19,2) NOT NULL,
  `ID_PRODUK` varchar(30) NOT NULL,
  PRIMARY KEY  (`ID_PULSA`),
  KEY `FK330CBBA8B7658619` (`ID_PRODUK`),
  CONSTRAINT `FK330CBBA8B7658619` FOREIGN KEY (`ID_PRODUK`) REFERENCES `mst_produk` (`ID_PRODUK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_RUNNING_NUMBER`
--

DROP TABLE IF EXISTS `MST_RUNNING_NUMBER`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_RUNNING_NUMBER` (
  `ID` varchar(255) NOT NULL,
  `NUMBER` int(11) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_SATUAN`
--

DROP TABLE IF EXISTS `MST_SATUAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_SATUAN` (
  `ID_SATUAN` varchar(10) NOT NULL,
  `NAMA` varchar(25) default NULL,
  PRIMARY KEY  (`ID_SATUAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_SHIFT`
--

DROP TABLE IF EXISTS `MST_SHIFT`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_SHIFT` (
  `ID_SHIFT` varchar(10) NOT NULL,
  `JAM_MULAI` time NOT NULL,
  `JAM_SELESAI` time NOT NULL,
  PRIMARY KEY  (`ID_SHIFT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `MST_SYSTEM_PROPERTY`
--

DROP TABLE IF EXISTS `MST_SYSTEM_PROPERTY`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `MST_SYSTEM_PROPERTY` (
  `ID` varchar(255) NOT NULL,
  `VAL` varchar(255) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SEC_MENU`
--

DROP TABLE IF EXISTS `SEC_MENU`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `SEC_MENU` (
  `ID_MENU` varchar(50) NOT NULL,
  `MENU_LEVEL` int(11) NOT NULL,
  `PANEL_CLASS` varchar(70) default NULL,
  `URUTAN` int(11) NOT NULL,
  `ID_PARENT` varchar(50) default NULL,
  PRIMARY KEY  (`ID_MENU`),
  KEY `FK67C2FCAD37D2A8C2` (`ID_PARENT`),
  CONSTRAINT `FK67C2FCAD37D2A8C2` FOREIGN KEY (`ID_PARENT`) REFERENCES `sec_menu` (`ID_MENU`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SEC_PENGGUNA`
--

DROP TABLE IF EXISTS `SEC_PENGGUNA`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `SEC_PENGGUNA` (
  `ID_PENGGUNA` varchar(30) NOT NULL,
  `KATA_SANDI` varchar(100) NOT NULL,
  `NAMA_LENGKAP` varchar(100) default NULL,
  PRIMARY KEY  (`ID_PENGGUNA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SEC_PERAN`
--

DROP TABLE IF EXISTS `SEC_PERAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `SEC_PERAN` (
  `ID_PERAN` varchar(20) NOT NULL,
  `DESKRIPSI` varchar(255) default NULL,
  PRIMARY KEY  (`ID_PERAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SEC_PERAN_MENU`
--

DROP TABLE IF EXISTS `SEC_PERAN_MENU`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `SEC_PERAN_MENU` (
  `ID_PERAN` varchar(20) NOT NULL,
  `ID_MENU` varchar(50) NOT NULL,
  KEY `FK55093362D0314E77` (`ID_MENU`),
  KEY `FK55093362364D277B` (`ID_PERAN`),
  CONSTRAINT `FK55093362364D277B` FOREIGN KEY (`ID_PERAN`) REFERENCES `sec_peran` (`ID_PERAN`),
  CONSTRAINT `FK55093362D0314E77` FOREIGN KEY (`ID_MENU`) REFERENCES `sec_menu` (`ID_MENU`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `SEC_PERAN_PENGGUNA`
--

DROP TABLE IF EXISTS `SEC_PERAN_PENGGUNA`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `SEC_PERAN_PENGGUNA` (
  `ID_PERAN` varchar(20) NOT NULL,
  `ID_PENGGUNA` varchar(30) NOT NULL,
  KEY `FKB39C7F12364D277B` (`ID_PERAN`),
  KEY `FKB39C7F12F84E457` (`ID_PENGGUNA`),
  CONSTRAINT `FKB39C7F12364D277B` FOREIGN KEY (`ID_PERAN`) REFERENCES `sec_peran` (`ID_PERAN`),
  CONSTRAINT `FKB39C7F12F84E457` FOREIGN KEY (`ID_PENGGUNA`) REFERENCES `sec_pengguna` (`ID_PENGGUNA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_PEMBAYARAN`
--

DROP TABLE IF EXISTS `TR_PEMBAYARAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_PEMBAYARAN` (
  `ID_PEMBAYARAN` varchar(15) NOT NULL,
  `JENIS_PEMBAYARAN` varchar(10) default NULL,
  `JUMLAH_UANG` decimal(19,2) default NULL,
  `KEMBALIAN` decimal(19,2) default NULL,
  `NILAI` decimal(19,2) default NULL,
  `NOMOR_VOUCHER` varchar(255) default NULL,
  `TOTAL_TRANSAKSI` decimal(19,2) default NULL,
  `ID_KARTU` varchar(10) default NULL,
  `ID_PENJUALAN` varchar(16) default NULL,
  PRIMARY KEY  (`ID_PEMBAYARAN`),
  KEY `FK9E2E6F81A61FC71D` (`ID_PENJUALAN`),
  KEY `FK9E2E6F811CD5FDDB` (`ID_KARTU`),
  CONSTRAINT `FK9E2E6F811CD5FDDB` FOREIGN KEY (`ID_KARTU`) REFERENCES `mst_kartu_bayar` (`ID_KARTU`),
  CONSTRAINT `FK9E2E6F81A61FC71D` FOREIGN KEY (`ID_PENJUALAN`) REFERENCES `tr_penjualan` (`ID_PENJUALAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_PEMBELIAN`
--

DROP TABLE IF EXISTS `TR_PEMBELIAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_PEMBELIAN` (
  `ID_PEMBELIAN` varchar(15) NOT NULL,
  `TANGGAL` date NOT NULL,
  `TOTAL` decimal(19,2) NOT NULL,
  `version` int(11) default NULL,
  PRIMARY KEY  (`ID_PEMBELIAN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_PEMBELIAN_DETAIL`
--

DROP TABLE IF EXISTS `TR_PEMBELIAN_DETAIL`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_PEMBELIAN_DETAIL` (
  `ID_PEMBELIAN_DETAIL` varchar(19) NOT NULL,
  `HARGA` decimal(19,2) NOT NULL,
  `KUANTITAS` int(11) NOT NULL,
  `SUB_TOTAL` decimal(19,2) default NULL,
  `ID_PEMBELIAN` varchar(15) NOT NULL,
  `ID_PRODUK` varchar(30) NOT NULL,
  PRIMARY KEY  (`ID_PEMBELIAN_DETAIL`),
  KEY `FKDA02F94CB7658619` (`ID_PRODUK`),
  KEY `FKDA02F94C1F4CC02F` (`ID_PEMBELIAN`),
  CONSTRAINT `FKDA02F94C1F4CC02F` FOREIGN KEY (`ID_PEMBELIAN`) REFERENCES `tr_pembelian` (`ID_PEMBELIAN`),
  CONSTRAINT `FKDA02F94CB7658619` FOREIGN KEY (`ID_PRODUK`) REFERENCES `mst_produk` (`ID_PRODUK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_PENJUALAN`
--

DROP TABLE IF EXISTS `TR_PENJUALAN`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_PENJUALAN` (
  `ID_PENJUALAN` varchar(16) NOT NULL,
  `TANGGAL` date default NULL,
  `TOTAL` decimal(19,2) default NULL,
  `version` int(11) default NULL,
  `ID_SESI` varchar(15) default NULL,
  PRIMARY KEY  (`ID_PENJUALAN`),
  KEY `FKD519731B81429438` (`ID_SESI`),
  CONSTRAINT `FKD519731B81429438` FOREIGN KEY (`ID_SESI`) REFERENCES `tr_sesi_kassa` (`ID_SESI`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_PENJUALAN_DETAIL`
--

DROP TABLE IF EXISTS `TR_PENJUALAN_DETAIL`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_PENJUALAN_DETAIL` (
  `ID_PENJUALAN_DETAIL` varchar(19) NOT NULL,
  `HARGA` decimal(19,2) NOT NULL,
  `HARGA1` decimal(19,2) NOT NULL,
  `KUANTITAS` int(11) NOT NULL,
  `KUANTITAS1` int(11) NOT NULL,
  `SUB_TOTAL` decimal(19,2) default NULL,
  `ID_PENJUALAN` varchar(16) NOT NULL,
  `ID_PRODUK` varchar(30) NOT NULL,
  `ID_PULSA` varchar(30) default NULL,
  PRIMARY KEY  (`ID_PENJUALAN_DETAIL`),
  KEY `FK221BF1F5583D08D8` (`ID_PULSA`),
  KEY `FK221BF1F5A61FC71D` (`ID_PENJUALAN`),
  KEY `FK221BF1F5B7658619` (`ID_PRODUK`),
  CONSTRAINT `FK221BF1F5583D08D8` FOREIGN KEY (`ID_PULSA`) REFERENCES `mst_pulsa_elektrik` (`ID_PULSA`),
  CONSTRAINT `FK221BF1F5A61FC71D` FOREIGN KEY (`ID_PENJUALAN`) REFERENCES `tr_penjualan` (`ID_PENJUALAN`),
  CONSTRAINT `FK221BF1F5B7658619` FOREIGN KEY (`ID_PRODUK`) REFERENCES `mst_produk` (`ID_PRODUK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_SALDO_STOK`
--

DROP TABLE IF EXISTS `TR_SALDO_STOK`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_SALDO_STOK` (
  `ID_SALDO_STOK` varchar(15) NOT NULL,
  `BELI` bigint(20) NOT NULL,
  `BULAN` varchar(2) NOT NULL,
  `JUAL` bigint(20) NOT NULL,
  `RETUR_BELI` bigint(20) NOT NULL,
  `RETUR_JUAL` bigint(20) NOT NULL,
  `SALDO_AWAL` bigint(20) NOT NULL,
  `TAHUN` varchar(4) NOT NULL,
  `TOTAL_BELI` bigint(20) NOT NULL,
  `ID_PRODUK` varchar(30) NOT NULL,
  PRIMARY KEY  (`ID_SALDO_STOK`),
  KEY `FK4424EE14B7658619` (`ID_PRODUK`),
  CONSTRAINT `FK4424EE14B7658619` FOREIGN KEY (`ID_PRODUK`) REFERENCES `mst_produk` (`ID_PRODUK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `TR_SESI_KASSA`
--

DROP TABLE IF EXISTS `TR_SESI_KASSA`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `TR_SESI_KASSA` (
  `ID_SESI` varchar(15) NOT NULL,
  `modal` decimal(19,2) default NULL,
  `tanggalLogin` datetime default NULL,
  `tanggalTutup` datetime default NULL,
  `totalSetoran` decimal(19,2) default NULL,
  `ID_PENGGUNA` varchar(30) default NULL,
  `ID_KASSA` varchar(10) default NULL,
  `ID_SHIFT` varchar(10) default NULL,
  PRIMARY KEY  (`ID_SESI`),
  KEY `FK8F098E5568670837` (`ID_KASSA`),
  KEY `FK8F098E55694E8F65` (`ID_SHIFT`),
  KEY `FK8F098E55F84E457` (`ID_PENGGUNA`),
  CONSTRAINT `FK8F098E5568670837` FOREIGN KEY (`ID_KASSA`) REFERENCES `mst_kassa` (`ID_KASSA`),
  CONSTRAINT `FK8F098E55694E8F65` FOREIGN KEY (`ID_SHIFT`) REFERENCES `mst_shift` (`ID_SHIFT`),
  CONSTRAINT `FK8F098E55F84E457` FOREIGN KEY (`ID_PENGGUNA`) REFERENCES `sec_pengguna` (`ID_PENGGUNA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2009-09-25  5:40:31
