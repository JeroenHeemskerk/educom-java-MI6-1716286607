-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 24 mei 2024 om 16:15
-- Serverversie: 10.4.32-MariaDB
-- PHP-versie: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mi6`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `agents`
--

CREATE TABLE `agents` (
  `id` int(255) NOT NULL,
  `service_id` int(255) NOT NULL,
  `passphrase` varchar(1000) NOT NULL,
  `retired` tinyint(1) NOT NULL,
  `licence_to_kill` tinyint(1) NOT NULL,
  `licence_valid` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `agents`
--

INSERT INTO `agents` (`id`, `service_id`, `passphrase`, `retired`, `licence_to_kill`, `licence_valid`) VALUES
(1, 2, 'Egalite, Fraternite, Kipsate.', 0, 1, '2026-08-23'),
(2, 5, 'nulnulvijf', 0, 1, '2024-05-01'),
(3, 7, 'Bond, James Bond.', 0, 1, '2025-07-17'),
(4, 30, 'Beter als 020!', 0, 0, '1909-07-04'),
(5, 102, 'Ik doe ook mee!', 0, 0, '1909-07-04'),
(6, 777, 'Bond, Bond Bond.', 1, 1, '2024-09-20');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `login_attempts`
--

CREATE TABLE `login_attempts` (
  `id` int(255) NOT NULL,
  `service_id` int(255) NOT NULL,
  `login_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `login_attempts`
--

INSERT INTO `login_attempts` (`id`, `service_id`, `login_stamp`, `success`) VALUES
(1, 450, '2024-05-23 16:15:56', 0),
(2, 320, '2024-05-23 17:17:15', 0),
(3, 320, '2024-05-23 17:17:26', 0),
(4, 320, '2024-05-23 17:17:37', 0),
(5, 230, '2024-05-24 10:09:04', 0),
(6, 5, '2024-05-24 10:51:37', 0),
(7, 302, '2024-05-24 10:52:04', 0),
(8, 7, '2024-05-24 11:29:18', 0),
(9, 7, '2024-05-24 11:31:02', 0),
(10, 7, '2024-05-24 11:37:24', 1),
(11, 7, '2024-05-24 11:38:07', 0),
(12, 7, '2024-05-24 11:38:20', 1),
(13, 7, '2024-05-24 12:04:54', 1),
(14, 777, '2024-05-24 13:10:28', 0),
(15, 777, '2024-05-24 13:11:38', 0),
(16, 777, '2024-05-24 14:24:21', 0),
(17, 777, '2024-05-24 14:29:34', 0),
(18, 5, '2024-05-24 14:32:01', 0),
(19, 5, '2024-05-24 14:35:10', 1),
(20, 2, '2024-05-24 14:44:08', 0),
(21, 2, '2024-05-24 14:46:49', 1),
(22, 30, '2024-05-24 14:47:52', 1),
(23, 453, '2024-05-24 15:26:56', 0),
(24, 323, '2024-05-24 15:27:53', 0),
(25, 777, '2024-05-24 15:28:04', 0),
(26, 7, '2024-05-24 16:05:06', 0),
(27, 7, '2024-05-24 16:08:02', 1),
(28, 7, '2024-05-24 16:08:02', 1),
(29, 7, '2024-05-24 16:11:20', 0),
(30, 7, '2024-05-24 16:13:45', 1),
(31, 777, '2024-05-24 16:14:11', 0);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `agents`
--
ALTER TABLE `agents`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `login_attempts`
--
ALTER TABLE `login_attempts`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `agents`
--
ALTER TABLE `agents`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT voor een tabel `login_attempts`
--
ALTER TABLE `login_attempts`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
