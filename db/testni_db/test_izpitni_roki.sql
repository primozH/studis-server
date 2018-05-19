-- --------------------------------------------------------
-- Strežnik:                     127.0.0.1
-- Verzija strežnika:            10.2.9-MariaDB - mariadb.org binary distribution
-- Operacijski sistem strežnika: Win64
-- HeidiSQL Različica:           9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for tabela studis.izpitni_rok
DROP TABLE IF EXISTS `izpitni_rok`;
CREATE TABLE IF NOT EXISTS `izpitni_rok` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`datum` date NOT NULL,
	`cas` time DEFAULT NULL,
	`prostor` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
	`izvajalec` int(11) DEFAULT NULL,
	`studijsko_leto` int(11) NOT NULL,
	`predmet` int(11) NOT NULL,
	PRIMARY KEY (`id`),
	KEY `FK_rok_izvajalec` (`izvajalec`),
	KEY `FK_rok_predmet` (`predmet`,`studijsko_leto`),
	CONSTRAINT `FK_rok_izvajalec` FOREIGN KEY (`izvajalec`) REFERENCES `uporabnik` (`id_uporabnik`),
	CONSTRAINT `FK_rok_predmet` FOREIGN KEY (`predmet`, `studijsko_leto`) REFERENCES `predmet_izvajanje` (`predmet`, `studijsko_leto`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Dumping data for table studis.izpitni_rok: ~34 rows (približno)
/*!40000 ALTER TABLE `izpitni_rok` DISABLE KEYS */;
INSERT INTO `izpitni_rok` (`id`, `datum`, `cas`, `prostor`, `izvajalec`, `studijsko_leto`, `predmet`) VALUES
	(2, '2019-02-19', '11:00:00', 'P01', 28, 2018, 63280),
	(3, '2019-09-03', '11:00:00', 'P01', 28, 2018, 63280),
	(4, '2019-06-05', '12:00:00', 'PA', 26, 2018, 63212),
	(5, '2019-06-20', '12:00:00', 'PA', 26, 2018, 63212),
	(6, '2019-08-28', '12:00:00', 'PA', 26, 2018, 63212),
	(7, '2019-06-06', '10:00:00', 'P04', 30, 2018, 63281),
	(8, '2019-06-20', '10:00:00', 'P04', 30, 2018, 63281),
	(9, '2019-08-30', '10:00:00', 'P04', 30, 2018, 63281),
	(10, '2019-01-22', '11:00:00', 'P01', 27, 2018, 63279),
	(11, '2019-01-24', '10:00:00', NULL, 15, 2018, 63203),
	(12, '2019-02-07', '10:00:00', NULL, 15, 2018, 63203),
	(19, '2019-06-10', '10:00:00', 'P01', 25, 2018, 63207),
	(20, '2019-02-05', '10:00:00', NULL, 28, 2018, 63280),
	(21, '2019-01-22', '10:00:00', 'PA', 22, 2018, 63205),
	(22, '2019-02-05', '10:00:00', 'PA', 22, 2018, 63205),
	(23, '2019-09-02', '10:00:00', 'PA', 22, 2018, 63205),
	(24, '2019-09-03', '10:00:00', 'PA', 28, 2018, 63217),
	(25, '2019-06-04', '11:00:00', 'PA', 28, 2018, 63217),
	(26, '2019-06-19', '11:00:00', 'PA', 28, 2018, 63217),
	(27, '2019-06-03', '11:00:00', 'PA', 10, 2018, 63218),
	(28, '2019-06-17', '11:00:00', 'PA', 10, 2018, 63218),
	(29, '2019-08-27', '11:00:00', 'PA', 10, 2018, 63218),
	(30, '2019-02-05', '11:00:00', 'PA', 12, 2018, 63258),
	(31, '2019-02-12', '11:00:00', 'PA', 12, 2018, 63258),
	(32, '2019-08-29', '11:00:00', 'PA', 12, 2018, 63258),
	(33, '2019-08-30', '11:00:00', 'PA', 25, 2018, 63207),
	(34, '2019-06-28', '11:00:00', 'PA', 25, 2018, 63207),
	(35, '2019-06-17', '11:00:00', 'PA', 25, 2018, 63207),
	(36, '2019-02-04', '11:00:00', 'PA', 28, 2018, 63283),
	(37, '2019-02-18', '09:00:00', 'PA', 28, 2018, 63283),
	(38, '2019-09-13', '15:00:00', 'PA', 28, 2018, 63283),
	(39, '2019-02-07', '09:00:00', 'PB', 27, 2018, 63279),
	(40, '2019-08-28', '09:00:00', 'PB', 27, 2018, 63279),
	(41, '2019-08-28', '09:00:00', 'PB', 20, 2018, 63219);
/*!40000 ALTER TABLE `izpitni_rok` ENABLE KEYS */;

-- Dumping structure for tabela studis.odjava
DROP TABLE IF EXISTS `odjava`;
CREATE TABLE IF NOT EXISTS `odjava` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`prijava_id` int(11) NOT NULL,
	`cas_odjave` datetime DEFAULT NULL,
	`odjavitelj` int(11) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FK_odjava_odjavitelj` (`odjavitelj`),
	KEY `FK_odjava_studijsko_leto` (`prijava_id`),
	CONSTRAINT `FK_odjava_odjavitelj` FOREIGN KEY (`odjavitelj`) REFERENCES `uporabnik` (`id_uporabnik`),
	CONSTRAINT `FK_odjava_prijava` FOREIGN KEY (`prijava_id`) REFERENCES `prijava_rok` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Dumping data for table studis.odjava: ~32 rows (približno)
/*!40000 ALTER TABLE `odjava` DISABLE KEYS */;
INSERT INTO `odjava` (`id`, `prijava_id`, `cas_odjave`, `odjavitelj`) VALUES
	(1, 3, '2018-05-15 16:22:03', 52),
	(2, 4, '2018-05-15 16:28:43', 52),
	(3, 4, '2018-05-15 16:29:57', 52),
	(4, 4, '2018-05-15 16:30:15', 52),
	(5, 5, '2018-05-15 20:45:31', 51),
	(6, 6, '2018-05-15 20:45:38', 51),
	(7, 7, '2018-05-15 21:07:24', 51),
	(8, 8, '2018-05-15 21:07:41', 51),
	(9, 9, '2018-05-15 21:08:38', 51),
	(10, 10, '2018-05-15 21:08:46', 51),
	(11, 11, '2018-05-15 21:12:00', 51),
	(12, 12, '2018-05-15 21:15:23', 51),
	(13, 13, '2018-05-15 21:15:33', 51),
	(14, 14, '2018-05-15 21:18:11', 51),
	(15, 15, '2018-05-15 21:18:19', 51),
	(16, 16, '2018-05-15 21:24:13', 51),
	(17, 17, '2018-05-15 21:29:25', 51),
	(18, 18, '2018-05-15 21:40:03', 51),
	(19, 19, '2018-05-15 21:41:32', 51),
	(20, 20, '2018-05-15 21:41:38', 51),
	(21, 21, '2018-05-15 21:47:17', 51),
	(22, 22, '2018-05-15 22:00:29', 51),
	(23, 23, '2018-05-15 22:00:41', 51),
	(25, 26, '2018-05-15 22:02:05', 57),
	(26, 25, '2018-05-15 22:02:06', 57),
	(27, 24, '2018-05-15 22:02:07', 57),
	(28, 27, '2018-05-15 22:04:34', 57),
	(29, 28, '2018-05-16 13:48:33', 56),
	(30, 30, '2018-05-16 14:56:35', 57),
	(31, 31, '2018-05-16 14:58:58', 2),
	(32, 32, '2018-05-16 15:02:07', 28),
	(33, 34, '2018-05-18 13:33:03', 57),
	(34, 35, '2018-05-18 13:34:08', 57),
	(35, 36, '2018-05-18 13:34:12', 57),
	(36, 37, '2018-05-18 13:34:18', 57);
/*!40000 ALTER TABLE `odjava` ENABLE KEYS */;

-- Dumping structure for tabela studis.prijava_rok
DROP TABLE IF EXISTS `prijava_rok`;
CREATE TABLE IF NOT EXISTS `prijava_rok` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`izpitni_rok` int(11) DEFAULT NULL,
	`cas_prijave` datetime NOT NULL,
	`student` int(11) NOT NULL,
	`cena` decimal(7,2) DEFAULT NULL,
	`valuta` varchar(20) COLLATE utf8_slovenian_ci DEFAULT NULL,
	`zakljucena` tinyint(1) DEFAULT 0,
	`brisana` tinyint(1) DEFAULT 0,
	PRIMARY KEY (`id`),
	KEY `FK_prijava_rok_uporabnik` (`student`),
	KEY `FK_prijava_rok_izpitni_rok` (`izpitni_rok`),
	CONSTRAINT `FK_prijava_rok_izpitni_rok` FOREIGN KEY (`izpitni_rok`) REFERENCES `izpitni_rok` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `FK_prijava_rok_uporabnik` FOREIGN KEY (`student`) REFERENCES `uporabnik` (`id_uporabnik`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Dumping data for table studis.prijava_rok: ~34 rows (približno)
/*!40000 ALTER TABLE `prijava_rok` DISABLE KEYS */;
INSERT INTO `prijava_rok` (`id`, `izpitni_rok`, `cas_prijave`, `student`, `cena`, `valuta`, `zakljucena`, `brisana`) VALUES
	(3, 4, '2018-05-15 16:19:35', 52, 0.00, NULL, 0, 1),
	(4, 4, '2018-05-15 16:28:01', 52, 0.00, NULL, 0, 1),
	(5, 4, '2018-05-15 16:42:39', 51, 0.00, NULL, 0, 1),
	(6, 4, '2018-05-15 20:45:36', 51, 0.00, NULL, 0, 1),
	(7, 4, '2018-05-15 20:50:00', 51, 0.00, NULL, 0, 1),
	(8, 5, '2018-05-15 21:07:28', 51, 0.00, NULL, 0, 1),
	(9, 5, '2018-05-15 21:07:46', 51, 0.00, NULL, 0, 1),
	(10, 6, '2018-05-15 21:08:42', 51, 0.00, NULL, 0, 1),
	(11, 4, '2018-05-15 21:08:50', 51, 0.00, NULL, 0, 1),
	(12, 5, '2018-05-15 21:12:13', 51, 0.00, NULL, 0, 1),
	(13, 4, '2018-05-15 21:15:27', 51, 0.00, NULL, 0, 1),
	(14, 5, '2018-05-15 21:15:42', 51, 0.00, NULL, 0, 1),
	(15, 4, '2018-05-15 21:18:15', 51, 0.00, NULL, 0, 1),
	(16, 5, '2018-05-15 21:24:05', 51, 0.00, NULL, 0, 1),
	(17, 4, '2018-05-15 21:24:23', 51, 0.00, NULL, 0, 1),
	(18, 4, '2018-05-15 21:29:28', 51, 0.00, NULL, 0, 1),
	(19, 4, '2018-05-15 21:40:06', 51, 0.00, NULL, 0, 1),
	(20, 4, '2018-05-15 21:41:36', 51, 0.00, NULL, 0, 1),
	(21, 4, '2018-05-15 21:41:39', 51, 0.00, NULL, 0, 1),
	(22, 4, '2018-05-15 21:47:21', 51, 0.00, NULL, 0, 1),
	(23, 5, '2018-05-15 22:00:34', 51, 0.00, NULL, 0, 1),
	(24, 8, '2018-05-15 22:01:38', 57, 0.00, NULL, 0, 1),
	(25, 4, '2018-05-15 22:01:40', 57, 0.00, NULL, 0, 1),
	(26, 3, '2018-05-15 22:01:46', 57, 0.00, NULL, 0, 1),
	(27, 11, '2018-05-15 22:04:30', 57, 0.00, NULL, 0, 1),
	(28, 2, '2018-05-16 13:48:28', 56, 0.00, NULL, 0, 1),
	(30, 2, '2018-05-16 14:21:59', 57, 0.00, NULL, 0, 1),
	(31, 2, '2018-05-16 14:57:01', 57, 0.00, NULL, 0, 1),
	(32, 2, '2018-05-16 14:59:31', 57, 0.00, NULL, 0, 1),
	(33, 2, '2018-05-16 15:02:33', 57, 0.00, NULL, 1, 0),
	(34, 2, '2018-05-18 13:26:12', 57, 0.00, NULL, 0, 1),
	(35, 2, '2018-05-18 13:34:07', 57, 0.00, NULL, 0, 1),
	(36, 4, '2018-05-18 13:34:11', 57, 0.00, NULL, 0, 1),
	(37, 2, '2018-05-18 13:34:14', 57, 0.00, NULL, 0, 1);
/*!40000 ALTER TABLE `prijava_rok` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;