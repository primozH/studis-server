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


-- Dumping database structure for studis
DROP DATABASE IF EXISTS `studis`;
CREATE DATABASE IF NOT EXISTS `studis` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_slovenian_ci */;
USE `studis`;

-- Dumping structure for tabela studis.cenik
CREATE TABLE IF NOT EXISTS `cenik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(50) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `cena` decimal(7,2) DEFAULT NULL,
  `valuta` varchar(10) COLLATE utf8_slovenian_ci DEFAULT 'EUR',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.del_predmetnika
CREATE TABLE IF NOT EXISTS `del_predmetnika` (
  `sifra` int(11) NOT NULL,
  `tip` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.drzava
CREATE TABLE IF NOT EXISTS `drzava` (
  `numericna_oznaka` int(11) NOT NULL,
  `iso_naziv` varchar(255) COLLATE utf8_slovenian_ci NOT NULL,
  `iso_koda` varchar(2) COLLATE utf8_slovenian_ci NOT NULL,
  `opombe` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `slovenski_naziv` varchar(255) COLLATE utf8_slovenian_ci NOT NULL,
  `iso_3_koda` varchar(3) COLLATE utf8_slovenian_ci NOT NULL,
  PRIMARY KEY (`numericna_oznaka`),
  UNIQUE KEY `iso_3_koda` (`iso_3_koda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.izpit
CREATE TABLE IF NOT EXISTS `izpit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prijava_id` int(11) DEFAULT NULL,
  `datum` date NOT NULL,
  `koncna_ocena` int(11) DEFAULT NULL,
  `ocena_pisno` int(11) DEFAULT NULL,
  `ocena_ustno` int(11) DEFAULT NULL,
  `st_polaganja_leto` int(11) NOT NULL,
  `st_polaganja_skupno` int(11) NOT NULL,
  `predmet` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `izpit_prijava_rok_prijava_id_fk` (`prijava_id`),
  KEY `izpit_predmet_sifra_fk` (`predmet`),
  CONSTRAINT `FK_izpit_studijsko_leto` FOREIGN KEY (`prijava_id`) REFERENCES `prijava_rok` (`id`),
  CONSTRAINT `izpit_predmet_sifra_fk` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`),
  CONSTRAINT `izpit_student_id_uporabnik_fk` FOREIGN KEY (`id`) REFERENCES `uporabnik` (`id_uporabnik`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.izpitni_rok
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
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.kandidat
CREATE TABLE IF NOT EXISTS `kandidat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) COLLATE utf8_slovenian_ci NOT NULL,
  `priimek` varchar(100) COLLATE utf8_slovenian_ci NOT NULL,
  `studijski_program` int(11) NOT NULL,
  `email` varchar(100) COLLATE utf8_slovenian_ci NOT NULL,
  `uporabnisko_ime` varchar(50) COLLATE utf8_slovenian_ci NOT NULL,
  `geslo_plain` varchar(30) COLLATE utf8_slovenian_ci NOT NULL,
  `vpisna_stevilka` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kandidat_vpisna_stevilka_uindex` (`vpisna_stevilka`),
  KEY `kandidat_studijski_program_sifra_evs_fk` (`studijski_program`),
  CONSTRAINT `kandidat_studijski_program_sifra_evs_fk` FOREIGN KEY (`studijski_program`) REFERENCES `studijski_program` (`sifra_evs`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.klasius
CREATE TABLE IF NOT EXISTS `klasius` (
  `sifra` int(11) NOT NULL,
  `opis` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `raven_izobrazbe` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `strokovni_naslov` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.letnik
CREATE TABLE IF NOT EXISTS `letnik` (
  `letnik` int(11) NOT NULL,
  PRIMARY KEY (`letnik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.nacin_studija
CREATE TABLE IF NOT EXISTS `nacin_studija` (
  `sifra` int(11) NOT NULL,
  `opis` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `opis_ang` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.napacna_prijava
CREATE TABLE IF NOT EXISTS `napacna_prijava` (
  `id_napacnega` int(11) NOT NULL AUTO_INCREMENT,
  `cas_poteka_izklopa` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `IP` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `st_napacnih_poskusov` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_napacnega`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.obcina
CREATE TABLE IF NOT EXISTS `obcina` (
  `sifra` int(11) NOT NULL,
  `ime` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.oblika_studija
CREATE TABLE IF NOT EXISTS `oblika_studija` (
  `sifra` int(11) NOT NULL,
  `opis` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `opis_ang` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.odjava
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

-- Data exporting was unselected.
-- Dumping structure for tabela studis.posta
CREATE TABLE IF NOT EXISTS `posta` (
  `postna_stevilka` int(11) NOT NULL,
  `naziv_poste` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`postna_stevilka`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.praznik
CREATE TABLE IF NOT EXISTS `praznik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datum` date NOT NULL,
  `ime` varchar(100) COLLATE utf8_slovenian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.predmet
CREATE TABLE IF NOT EXISTS `predmet` (
  `sifra` int(11) NOT NULL,
  `ects` int(11) DEFAULT NULL,
  `naziv` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `semester` int(11) DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.predmetnik
CREATE TABLE IF NOT EXISTS `predmetnik` (
  `modul` int(11) DEFAULT NULL,
  `del_predmetnika` int(11) NOT NULL,
  `letnik` int(11) NOT NULL,
  `predmet` int(11) NOT NULL,
  `studijski_program` int(11) NOT NULL,
  `studijsko_leto` int(11) NOT NULL,
  PRIMARY KEY (`del_predmetnika`,`letnik`,`predmet`,`studijski_program`,`studijsko_leto`),
  KEY `FK_predmetnik_letnik` (`letnik`),
  KEY `FK_predmetnik_predmet` (`predmet`),
  KEY `FK_predmetnik_studijski_program` (`studijski_program`),
  KEY `FK_predmetnik_studijsko_leto` (`studijsko_leto`),
  CONSTRAINT `FK_predmetnik_del_predmetnika` FOREIGN KEY (`del_predmetnika`) REFERENCES `del_predmetnika` (`sifra`),
  CONSTRAINT `FK_predmetnik_letnik` FOREIGN KEY (`letnik`) REFERENCES `letnik` (`letnik`),
  CONSTRAINT `FK_predmetnik_predmet` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`),
  CONSTRAINT `FK_predmetnik_studijski_program` FOREIGN KEY (`studijski_program`) REFERENCES `studijski_program` (`sifra_evs`),
  CONSTRAINT `FK_predmetnik_studijsko_leto` FOREIGN KEY (`studijsko_leto`) REFERENCES `studijsko_leto` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.predmet_izvajanje
CREATE TABLE IF NOT EXISTS `predmet_izvajanje` (
  `nosilec1` int(11) DEFAULT NULL,
  `nosilec2` int(11) DEFAULT NULL,
  `nosilec3` int(11) DEFAULT NULL,
  `predmet` int(11) NOT NULL,
  `studijsko_leto` int(11) NOT NULL,
  PRIMARY KEY (`predmet`,`studijsko_leto`),
  KEY `FK_predmet_izvajanje_studijsko_leto` (`studijsko_leto`),
  KEY `FK_predmet_izvajanje_nosilec1` (`nosilec1`),
  KEY `FK_predmet_izvajanje_nosilec3` (`nosilec3`),
  KEY `FK_predmet_izvajanje_nosilec2` (`nosilec2`),
  CONSTRAINT `FK_predmet_izvajanje_nosilec1` FOREIGN KEY (`nosilec1`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_predmet_izvajanje_nosilec2` FOREIGN KEY (`nosilec2`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_predmet_izvajanje_nosilec3` FOREIGN KEY (`nosilec3`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_predmet_izvajanje_predmet` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`),
  CONSTRAINT `FK_predmet_izvajanje_studijsko_leto` FOREIGN KEY (`studijsko_leto`) REFERENCES `studijsko_leto` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.predmet_student
CREATE TABLE IF NOT EXISTS `predmet_student` (
  `predmet` int(11) NOT NULL,
  `studijsko_leto` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  PRIMARY KEY (`predmet`,`studijsko_leto`,`student`),
  KEY `FK_predmet_student_student` (`student`,`studijsko_leto`),
  CONSTRAINT `FK_predmet_student_predmet` FOREIGN KEY (`predmet`) REFERENCES `predmet` (`sifra`) ON UPDATE CASCADE,
  CONSTRAINT `FK_predmet_student_student` FOREIGN KEY (`student`, `studijsko_leto`) REFERENCES `vpis` (`student`, `studijsko_leto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.prijava_rok
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
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.referent
CREATE TABLE IF NOT EXISTS `referent` (
  `id_uporabnik` int(11) NOT NULL,
  PRIMARY KEY (`id_uporabnik`),
  CONSTRAINT `FK_referent_id_uporabnik` FOREIGN KEY (`id_uporabnik`) REFERENCES `uporabnik` (`id_uporabnik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.skrbnik
CREATE TABLE IF NOT EXISTS `skrbnik` (
  `id_uporabnik` int(11) NOT NULL,
  PRIMARY KEY (`id_uporabnik`),
  CONSTRAINT `FK_skrbnik_id_uporabnik` FOREIGN KEY (`id_uporabnik`) REFERENCES `uporabnik` (`id_uporabnik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.student
CREATE TABLE IF NOT EXISTS `student` (
  `id_uporabnik` int(11) NOT NULL,
  `drzava_rojstva` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `kraj_rojstva` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `naslov_stalno` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `privzeti_naslov` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `naslov_zacasno` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `obcina_rojstva` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `tel_stevilka` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `vpisna_stevilka` int(11) DEFAULT NULL,
  `drzava_stalno` int(11) DEFAULT NULL,
  `drzava_zacasno` int(11) DEFAULT NULL,
  `obcina_stalno` int(11) DEFAULT NULL,
  `obcina_zacasno` int(11) DEFAULT NULL,
  `posta_stalno` int(11) DEFAULT NULL,
  `posta_zacasno` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_uporabnik`),
  KEY `FK_student_drzava_zacasno` (`drzava_zacasno`),
  KEY `FK_student_obcina_stalno` (`obcina_stalno`),
  KEY `FK_student_obcina_zacasno` (`obcina_zacasno`),
  KEY `FK_student_posta_zacasno` (`posta_zacasno`),
  KEY `FK_student_drzava_stalno` (`drzava_stalno`),
  KEY `FK_student_posta_stalno` (`posta_stalno`),
  CONSTRAINT `FK_student_drzava_stalno` FOREIGN KEY (`drzava_stalno`) REFERENCES `drzava` (`numericna_oznaka`),
  CONSTRAINT `FK_student_drzava_zacasno` FOREIGN KEY (`drzava_zacasno`) REFERENCES `drzava` (`numericna_oznaka`),
  CONSTRAINT `FK_student_id_uporabnik` FOREIGN KEY (`id_uporabnik`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_student_obcina_stalno` FOREIGN KEY (`obcina_stalno`) REFERENCES `obcina` (`sifra`),
  CONSTRAINT `FK_student_obcina_zacasno` FOREIGN KEY (`obcina_zacasno`) REFERENCES `obcina` (`sifra`),
  CONSTRAINT `FK_student_posta_stalno` FOREIGN KEY (`posta_stalno`) REFERENCES `posta` (`postna_stevilka`),
  CONSTRAINT `FK_student_posta_zacasno` FOREIGN KEY (`posta_zacasno`) REFERENCES `posta` (`postna_stevilka`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.studijski_program
CREATE TABLE IF NOT EXISTS `studijski_program` (
  `sifra_evs` int(11) NOT NULL,
  `naziv` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `sifra` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `stevilo_semestrov` int(11) DEFAULT NULL,
  `stopnja` int(11) DEFAULT NULL,
  `klasius` int(11) DEFAULT NULL,
  PRIMARY KEY (`sifra_evs`),
  KEY `FK_studijski_program_klasius` (`klasius`),
  CONSTRAINT `FK_studijski_program_klasius` FOREIGN KEY (`klasius`) REFERENCES `klasius` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.studijsko_leto
CREATE TABLE IF NOT EXISTS `studijsko_leto` (
  `sifra` int(11) NOT NULL,
  `studijsko_leto` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.ucitelj
CREATE TABLE IF NOT EXISTS `ucitelj` (
  `id_uporabnik` int(11) NOT NULL,
  PRIMARY KEY (`id_uporabnik`),
  CONSTRAINT `FK_ucitelj_id_uporabnik` FOREIGN KEY (`id_uporabnik`) REFERENCES `uporabnik` (`id_uporabnik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.uporabnik
CREATE TABLE IF NOT EXISTS `uporabnik` (
  `id_uporabnik` int(11) NOT NULL AUTO_INCREMENT,
  `tip` varchar(31) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `datum_rojstva` date DEFAULT NULL,
  `davcna_stevilka` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_slovenian_ci NOT NULL,
  `emso` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `geslo` varchar(255) COLLATE utf8_slovenian_ci NOT NULL,
  `ime` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `priimek` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  `spol` int(11) DEFAULT NULL,
  `spremenjeno` datetime DEFAULT NULL,
  `uporabnisko_ime` varchar(255) COLLATE utf8_slovenian_ci NOT NULL,
  `ustvarjeno` datetime DEFAULT NULL,
  `zadnja_prijava` datetime DEFAULT NULL,
  `drzavljanstvo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_uporabnik`),
  KEY `FK_uporabnik_drzavljanstvo` (`drzavljanstvo`),
  CONSTRAINT `FK_uporabnik_drzavljanstvo` FOREIGN KEY (`drzavljanstvo`) REFERENCES `drzava` (`numericna_oznaka`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.vpis
CREATE TABLE IF NOT EXISTS `vpis` (
  `potrjen` tinyint(1) DEFAULT 0,
  `letnik` int(11) DEFAULT NULL,
  `nacin_studija` int(11) DEFAULT NULL,
  `oblika_studija` int(11) DEFAULT NULL,
  `student` int(11) NOT NULL,
  `studijski_program` int(11) DEFAULT NULL,
  `studijsko_leto` int(11) NOT NULL,
  `vrsta_vpisa` int(11) NOT NULL,
  PRIMARY KEY (`student`,`studijsko_leto`),
  KEY `FK_vpis_oblika_studija` (`oblika_studija`),
  KEY `FK_vpis_vrsta_vpisa` (`vrsta_vpisa`),
  KEY `FK_vpis_nacin_studija` (`nacin_studija`),
  KEY `FK_vpis_letnik` (`letnik`),
  KEY `FK_vpis_studijsko_leto` (`studijsko_leto`),
  KEY `FK_vpis_studijski_program` (`studijski_program`),
  CONSTRAINT `FK_vpis_letnik` FOREIGN KEY (`letnik`) REFERENCES `letnik` (`letnik`),
  CONSTRAINT `FK_vpis_nacin_studija` FOREIGN KEY (`nacin_studija`) REFERENCES `nacin_studija` (`sifra`),
  CONSTRAINT `FK_vpis_oblika_studija` FOREIGN KEY (`oblika_studija`) REFERENCES `oblika_studija` (`sifra`),
  CONSTRAINT `FK_vpis_student` FOREIGN KEY (`student`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_vpis_studijski_program` FOREIGN KEY (`studijski_program`) REFERENCES `studijski_program` (`sifra_evs`),
  CONSTRAINT `FK_vpis_studijsko_leto` FOREIGN KEY (`studijsko_leto`) REFERENCES `studijsko_leto` (`sifra`),
  CONSTRAINT `FK_vpis_vrsta_vpisa` FOREIGN KEY (`vrsta_vpisa`) REFERENCES `vrsta_vpisa` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.vrsta_vpisa
CREATE TABLE IF NOT EXISTS `vrsta_vpisa` (
  `sifra` int(11) NOT NULL,
  `vrsta_vpisa` varchar(255) COLLATE utf8_slovenian_ci DEFAULT NULL,
  PRIMARY KEY (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
-- Dumping structure for tabela studis.zeton
CREATE TABLE IF NOT EXISTS `zeton` (
  `izkoriscen` tinyint(1) DEFAULT 0,
  `prosta_izbira` tinyint(1) DEFAULT 0,
  `letnik` int(11) DEFAULT NULL,
  `nacin_studija` int(11) DEFAULT NULL,
  `oblika_studija` int(11) DEFAULT NULL,
  `student` int(11) NOT NULL,
  `studijski_program` int(11) DEFAULT NULL,
  `studijsko_leto` int(11) DEFAULT NULL,
  `vrsta_vpisa` int(11) NOT NULL,
  PRIMARY KEY (`student`,`vrsta_vpisa`),
  KEY `FK_zeton_studijsko_leto` (`studijsko_leto`),
  KEY `FK_zeton_nacin_studija` (`nacin_studija`),
  KEY `FK_zeton_letnik` (`letnik`),
  KEY `FK_zeton_studijski_program` (`studijski_program`),
  KEY `FK_zeton_oblika_studija` (`oblika_studija`),
  KEY `FK_zeton_vrsta_vpisa` (`vrsta_vpisa`),
  CONSTRAINT `FK_zeton_letnik` FOREIGN KEY (`letnik`) REFERENCES `letnik` (`letnik`),
  CONSTRAINT `FK_zeton_nacin_studija` FOREIGN KEY (`nacin_studija`) REFERENCES `nacin_studija` (`sifra`),
  CONSTRAINT `FK_zeton_oblika_studija` FOREIGN KEY (`oblika_studija`) REFERENCES `oblika_studija` (`sifra`),
  CONSTRAINT `FK_zeton_student` FOREIGN KEY (`student`) REFERENCES `uporabnik` (`id_uporabnik`),
  CONSTRAINT `FK_zeton_studijski_program` FOREIGN KEY (`studijski_program`) REFERENCES `studijski_program` (`sifra_evs`),
  CONSTRAINT `FK_zeton_studijsko_leto` FOREIGN KEY (`studijsko_leto`) REFERENCES `studijsko_leto` (`sifra`),
  CONSTRAINT `FK_zeton_vrsta_vpisa` FOREIGN KEY (`vrsta_vpisa`) REFERENCES `vrsta_vpisa` (`sifra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
