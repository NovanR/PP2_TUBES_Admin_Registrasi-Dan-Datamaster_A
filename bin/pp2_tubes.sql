-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 18, 2024 at 04:13 AM
-- Server version: 8.0.30
-- PHP Version: 8.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pp2_tubes`
--

-- --------------------------------------------------------

--
-- Table structure for table `dropbox`
--

CREATE TABLE `dropbox` (
  `idTps` int NOT NULL,
  `namaTps` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `noHpTps` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dropbox`
--

INSERT INTO `dropbox` (`idTps`, `namaTps`, `noHpTps`, `alamat`) VALUES
(1, 'TPS 1', '081200000001', 'Jl. Kebon Jeruk No. 1'),
(2, 'TPS 2', '081200000002', 'Jl. Mangga No. 2'),
(3, 'TPS 3', '081200000003', 'Jl. Melati No. 3'),
(4, 'TPS 4', '081200000004', 'Jl. Mawar No. 4'),
(5, 'TPS 5', '081200000005', 'Jl. Cemara No. 5'),
(6, 'TPS 6', '081200000006', 'Jl. Jambu No. 6'),
(7, 'TPS 7', '081200000007', 'Jl. Rambutan No. 7'),
(8, 'TPS 8', '081200000008', 'Jl. Durian No. 8'),
(9, 'TPS 9', '081200000009', 'Jl. Pepaya No. 9'),
(10, 'TPS 10', '081200000010', 'Jl. Apel No. 10');

-- --------------------------------------------------------

--
-- Table structure for table `kurir`
--

CREATE TABLE `kurir` (
  `idKurir` int NOT NULL,
  `namaKurir` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `jenisKelamin` enum('Laki-laki','Perempuan') COLLATE utf8mb4_general_ci NOT NULL,
  `tanggalLahir` date NOT NULL,
  `noHP` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('DISETUJUI','DITOLAK','PENDING') COLLATE utf8mb4_general_ci DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kurir`
--

INSERT INTO `kurir` (`idKurir`, `namaKurir`, `jenisKelamin`, `tanggalLahir`, `noHP`, `alamat`, `image`, `status`) VALUES
(1, 'Andi', 'Laki-laki', '1990-04-11', '081234500001', 'Jl. Melon No. 1', 'kurir1.jpg', 'DISETUJUI'),
(2, 'Bella', 'Perempuan', '1992-08-15', '081234500002', 'Jl. Jeruk No. 2', 'kurir2.jpg', 'PENDING'),
(3, 'Cindy', 'Perempuan', '1995-12-22', '081234500003', 'Jl. Mangga No. 3', 'kurir3.jpg', 'DITOLAK'),
(4, 'Dimas', 'Laki-laki', '1998-03-05', '081234500004', 'Jl. Durian No. 4', 'kurir4.jpg', 'DISETUJUI'),
(5, 'Erik', 'Laki-laki', '1991-05-18', '081234500005', 'Jl. Rambutan No. 5', 'kurir5.jpg', 'PENDING'),
(6, 'Fanny', 'Perempuan', '1994-09-10', '081234500006', 'Jl. Apel No. 6', 'kurir6.jpg', 'DITOLAK'),
(7, 'Gilang', 'Laki-laki', '1996-01-30', '081234500007', 'Jl. Anggur No. 7', 'kurir7.jpg', 'DISETUJUI'),
(8, 'Hana', 'Perempuan', '1997-06-06', '081234500008', 'Jl. Sawo No. 8', 'kurir8.jpg', 'DISETUJUI'),
(9, 'Iqbal', 'Laki-laki', '1989-11-25', '081234500009', 'Jl. Alpukat No. 9', 'kurir9.jpg', 'PENDING'),
(10, 'Jasmine', 'Perempuan', '1993-10-16', '081234500010', 'Jl. Pisang No. 10', 'kurir10.jpg', 'DISETUJUI');

-- --------------------------------------------------------

--
-- Table structure for table `masyarakat`
--

CREATE TABLE `masyarakat` (
  `idMasyarakat` int NOT NULL,
  `namaMasyarakat` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `jenisKelamin` enum('Laki-laki','Perempuan') COLLATE utf8mb4_general_ci NOT NULL,
  `tanggalLahir` date NOT NULL,
  `noHP` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('DISETUJUI','DITOLAK','PENDING') COLLATE utf8mb4_general_ci DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `masyarakat`
--

INSERT INTO `masyarakat` (`idMasyarakat`, `namaMasyarakat`, `jenisKelamin`, `tanggalLahir`, `noHP`, `alamat`, `image`, `status`) VALUES
(1, 'Ahmad', 'Laki-laki', '1990-01-01', '081234567890', 'Jl. Kebon Jeruk No. 1', 'img1.jpg', 'DISETUJUI'),
(2, 'Budi', 'Laki-laki', '1995-03-12', '081345678901', 'Jl. Mangga No. 2', 'img2.jpg', 'PENDING'),
(3, 'Citra', 'Perempuan', '1993-05-25', '081456789012', 'Jl. Melati No. 3', 'img3.jpg', 'DISETUJUI'),
(4, 'Dewi', 'Perempuan', '1998-07-07', '081567890123', 'Jl. Mawar No. 4', 'img4.jpg', 'DITOLAK'),
(5, 'Eka', 'Laki-laki', '2000-10-10', '081678901234', 'Jl. Cemara No. 5', 'img5.jpg', 'DISETUJUI'),
(6, 'Fajar', 'Laki-laki', '1992-09-15', '081789012345', 'Jl. Jambu No. 6', 'img6.jpg', 'PENDING'),
(7, 'Gita', 'Perempuan', '1997-12-20', '081890123456', 'Jl. Rambutan No. 7', 'img7.jpg', 'DISETUJUI'),
(8, 'Hendra', 'Laki-laki', '1988-11-30', '081901234567', 'Jl. Durian No. 8', 'img8.jpg', 'DITOLAK'),
(9, 'Indah', 'Perempuan', '1991-02-14', '082012345678', 'Jl. Pepaya No. 9', 'img9.jpg', 'DISETUJUI'),
(10, 'Joko', 'Laki-laki', '1994-06-18', '082123456789', 'Jl. Apel No. 10', 'img10.jpg', 'PENDING');

-- --------------------------------------------------------

--
-- Table structure for table `sampah`
--

CREATE TABLE `sampah` (
  `idSampah` int NOT NULL,
  `kategoriSampah` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `berat` int NOT NULL,
  `poin` int NOT NULL,
  `idMasyarakat` int NOT NULL,
  `idTps` int NOT NULL,
  `idKurir` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sampah`
--

INSERT INTO `sampah` (`idSampah`, `kategoriSampah`, `berat`, `poin`, `idMasyarakat`, `idTps`, `idKurir`) VALUES
(1, 'Plastik', 5, 10, 1, 1, 1),
(2, 'Kaca', 3, 7, 2, 2, 2),
(3, 'Kertas', 4, 8, 3, 3, 3),
(4, 'Logam', 6, 12, 4, 4, 4),
(5, 'Plastik', 7, 14, 5, 5, 5),
(6, 'Kaca', 2, 5, 6, 6, 6),
(7, 'Kertas', 8, 16, 7, 7, 7),
(8, 'Logam', 3, 6, 8, 8, 8),
(9, 'Plastik', 10, 20, 9, 9, 9),
(10, 'Kaca', 5, 10, 10, 10, 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dropbox`
--
ALTER TABLE `dropbox`
  ADD PRIMARY KEY (`idTps`);

--
-- Indexes for table `kurir`
--
ALTER TABLE `kurir`
  ADD PRIMARY KEY (`idKurir`);

--
-- Indexes for table `masyarakat`
--
ALTER TABLE `masyarakat`
  ADD PRIMARY KEY (`idMasyarakat`);

--
-- Indexes for table `sampah`
--
ALTER TABLE `sampah`
  ADD PRIMARY KEY (`idSampah`),
  ADD KEY `idMasyarakat` (`idMasyarakat`),
  ADD KEY `idTps` (`idTps`),
  ADD KEY `idKurir` (`idKurir`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dropbox`
--
ALTER TABLE `dropbox`
  MODIFY `idTps` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `kurir`
--
ALTER TABLE `kurir`
  MODIFY `idKurir` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `masyarakat`
--
ALTER TABLE `masyarakat`
  MODIFY `idMasyarakat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `sampah`
--
ALTER TABLE `sampah`
  MODIFY `idSampah` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sampah`
--
ALTER TABLE `sampah`
  ADD CONSTRAINT `sampah_ibfk_1` FOREIGN KEY (`idMasyarakat`) REFERENCES `masyarakat` (`idMasyarakat`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `sampah_ibfk_2` FOREIGN KEY (`idTps`) REFERENCES `dropbox` (`idTps`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `sampah_ibfk_3` FOREIGN KEY (`idKurir`) REFERENCES `kurir` (`idKurir`) ON DELETE CASCADE ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
