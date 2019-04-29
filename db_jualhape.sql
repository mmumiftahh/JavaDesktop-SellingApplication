-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2018 at 04:59 PM
-- Server version: 10.1.24-MariaDB
-- PHP Version: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_jualhape`
--

-- --------------------------------------------------------

--
-- Table structure for table `input_hp`
--

CREATE TABLE `input_hp` (
  `id_hp` varchar(15) NOT NULL,
  `merek_hp` varchar(30) NOT NULL,
  `warna_hp` varchar(20) NOT NULL,
  `harga_hp` int(11) NOT NULL,
  `stok_hp` int(11) NOT NULL,
  `hapus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `input_hp`
--

INSERT INTO `input_hp` (`id_hp`, `merek_hp`, `warna_hp`, `harga_hp`, `stok_hp`, `hapus`) VALUES
('H00001', 'Samsung Galaxy', 'Hijau', 700, 72, 0),
('H00002', 'Apple Iphone X', 'Kuning', 100, 30, 0),
('H00003', 'Xiaomi Redmi', 'Merah', 500, 22, 0),
('H00005', 'Asus', 'Pink', 150, 8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `keranjang`
--

CREATE TABLE `keranjang` (
  `id_beli` varchar(15) NOT NULL,
  `id_hp` varchar(15) NOT NULL,
  `merek_hp` varchar(50) NOT NULL,
  `warna_hp` varchar(30) NOT NULL,
  `harga_hp` int(11) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `total_harga` int(11) NOT NULL,
  `hapus` int(11) NOT NULL,
  `ketera` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `keranjang`
--

INSERT INTO `keranjang` (`id_beli`, `id_hp`, `merek_hp`, `warna_hp`, `harga_hp`, `jumlah_beli`, `total_harga`, `hapus`, `ketera`) VALUES
('P00001', 'H00002', 'Apple Iphone X', 'Kuning', 100, 5, 500, 0, 'hilang'),
('P00001', 'H00001', 'Samsung Galaxy', 'Hijau', 700, 1, 700, 0, 'hilang');

--
-- Triggers `keranjang`
--
DELIMITER $$
CREATE TRIGGER `pengurangan_stok` AFTER INSERT ON `keranjang` FOR EACH ROW BEGIN INSERT INTO
input_hp SET id_hp = new.id_hp, stok_hp = new.jumlah_beli
ON DUPLICATE KEY UPDATE stok_hp = stok_hp-new.jumlah_beli;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `id_beli` varchar(15) NOT NULL,
  `nama_pembeli` varchar(100) NOT NULL,
  `total_keseluruhan` int(11) NOT NULL,
  `uang_masuk` int(11) NOT NULL,
  `uang_kembali` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`id_beli`, `nama_pembeli`, `total_keseluruhan`, `uang_masuk`, `uang_kembali`) VALUES
('P00001', 'Miftah', 1200, 2000, 800);

-- --------------------------------------------------------

--
-- Stand-in structure for view `struk`
-- (See below for the actual view)
--
CREATE TABLE `struk` (
`id_beli` varchar(15)
,`id_hp` varchar(15)
,`merek_hp` varchar(50)
,`warna_hp` varchar(30)
,`harga_hp` int(11)
,`jumlah_beli` int(11)
,`total_keseluruhan` int(11)
,`uang_masuk` int(11)
,`uang_kembali` int(11)
);

-- --------------------------------------------------------

--
-- Structure for view `struk`
--
DROP TABLE IF EXISTS `struk`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `struk`  AS  select `keranjang`.`id_beli` AS `id_beli`,`keranjang`.`id_hp` AS `id_hp`,`keranjang`.`merek_hp` AS `merek_hp`,`keranjang`.`warna_hp` AS `warna_hp`,`keranjang`.`harga_hp` AS `harga_hp`,`keranjang`.`jumlah_beli` AS `jumlah_beli`,`pembelian`.`total_keseluruhan` AS `total_keseluruhan`,`pembelian`.`uang_masuk` AS `uang_masuk`,`pembelian`.`uang_kembali` AS `uang_kembali` from (`keranjang` join `pembelian` on((`keranjang`.`id_beli` = `pembelian`.`id_beli`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `input_hp`
--
ALTER TABLE `input_hp`
  ADD PRIMARY KEY (`id_hp`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`id_beli`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
