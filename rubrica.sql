-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Creato il: Set 21, 2016 alle 23:13
-- Versione del server: 10.1.10-MariaDB
-- Versione PHP: 7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rubrica`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `anagrafica`
--

CREATE TABLE `anagrafica` (
  `Id` int(11) UNSIGNED NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Citta` varchar(30) NOT NULL,
  `Indirizzo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `anagrafica`
--

INSERT INTO `anagrafica` (`Id`, `Cognome`, `Nome`, `Citta`, `Indirizzo`) VALUES
(1, 'Rossi', 'Mario', 'Palermo', 'Via Torino, 9'),
(2, 'Doe', 'John', 'New York', '2° St, 25'),
(3, 'Verdi', 'Giovanni', 'Alcamo', 'Via di qua, 131'),
(5, 'Alderson', 'Elliot', 'Unknown', 'Unknown, unknown');

-- --------------------------------------------------------

--
-- Struttura della tabella `numeri`
--

CREATE TABLE `numeri` (
  `Id` int(11) NOT NULL,
  `Contatto` int(11) NOT NULL,
  `Numero` varchar(20) DEFAULT NULL,
  `Tipo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `numeri`
--

INSERT INTO `numeri` (`Id`, `Contatto`, `Numero`, `Tipo`) VALUES
(1, 1, 'Cellulare', '333 123 4567'),
(2, 2, '0924 123456', 'Casa'),
(5, 1, 'Privato', '091 111111'),
(6, 3, '3332222222', 'Cellulare'),
(7, 4, '3333333333', 'Cellulare'),
(8, 5, '0911111111', 'Casa'),
(9, 1, 'casa', '111111');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `anagrafica`
--
ALTER TABLE `anagrafica`
  ADD PRIMARY KEY (`Id`);

--
-- Indici per le tabelle `numeri`
--
ALTER TABLE `numeri`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `anagrafica`
--
ALTER TABLE `anagrafica`
  MODIFY `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT per la tabella `numeri`
--
ALTER TABLE `numeri`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
