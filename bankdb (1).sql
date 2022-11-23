-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2022 at 12:02 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bankdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_pk` varchar(100) NOT NULL,
  `username` varchar(250) NOT NULL,
  `password` varchar(100) NOT NULL,
  `balance` double(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_pk`, `username`, `password`, `balance`) VALUES
('E15966', 'Doctor Who', 'BigBlueBox', 5300),
('O02620', 'Mathew McConaughey', 'Interstellar', 96360),
('Q55379', 'george Jumbe', 'George', 999970000),
('W14867', 'Kudakwashe Henry Mushunje', 'WoodChuckCouldChuckWood', 36000);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `account_fk` varchar(100) NOT NULL,
  `details` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `account_fk`, `details`) VALUES
(1, 'W14867', 'Deposited: $5000.00\nOn: 19-Oct-2022 03:42:49\nNew Balance: $5000.00'),
(2, 'W14867', 'Deposited: $200.00\nOn: 19-Oct-2022 03:43:04\nNew Balance: $5200.00'),
(3, 'O02620', 'Deposited: $160.00\nOn: 19-Oct-2022 03:57:26\nNew Balance: $160.00'),
(4, 'O02620', 'Received: $200.00\nFrom Account: W14867\nHolder: Kudakwashe Henry Mushunje\nOn: 19-Oct-2022 03:58:59'),
(5, 'W14867', 'Transferred: $200.00\nTo Account: O02620\nHolder: Mathew McConaughey\nOn: 19-Oct-2022 03:58:59\nNew Balance: $5000.00'),
(6, 'O02620', 'Received: $300.00\nFrom Account: W14867\nHolder: Kudakwashe Henry Mushunje\nOn: 19-Oct-2022 04:04:27\nNew Balance: $660.00'),
(7, 'W14867', 'Deposited: $1000.00\nOn: 19-Oct-2022 04:04:06\nNew Balance: $6000.00'),
(8, 'W14867', 'Transferred: $300.00\nTo Account: O02620\nHolder: Mathew McConaughey\nOn: 19-Oct-2022 04:04:27\nNew Balance: $5700.00'),
(9, 'O02620', 'Transferred: $300.00\nTo Account: W14867\nHolder: Kudakwashe Henry Mushunje\nOn: 19-Oct-2022 04:15:14\nNew Balance: $360.00'),
(10, 'W14867', 'Received: $300.00\nFrom Account: O02620\nHolder: Mathew McConaughey\nOn: 19-Oct-2022 04:15:14\nNew Balance: $6000.00'),
(11, 'O02620', 'Deposited: $100000.00\nOn: 19-Oct-2022 04:18:22\nNew Balance: $100360.00'),
(12, 'O02620', 'Transferred: $30000.00\nTo Account: W14867\nHolder: Kudakwashe Henry Mushunje\nOn: 19-Oct-2022 04:18:53\nNew Balance: $70360.00'),
(13, 'W14867', 'Received: $30000.00\nFrom Account: O02620\nHolder: Mathew McConaughey\nOn: 19-Oct-2022 04:18:53\nNew Balance: $36000.00'),
(14, 'E15966', 'Received: $4000.00\nFrom Account: O02620\nHolder: Mathew McConaughey\nOn: 26-Oct-2022 09:47:02\nNew Balance: $4000.00'),
(15, 'O02620', 'Transferred: $4000.00\nTo Account: E15966\nHolder: Doctor Who\nOn: 26-Oct-2022 09:47:02\nNew Balance: $66360.00'),
(16, 'Q55379', 'Deposited: $1000000000.00\nOn: 26-Oct-2022 10:51:54\nNew Balance: $1000000000.00'),
(17, 'Q55379', 'Transferred: $30000.00\nTo Account: O02620\nHolder: Mathew McConaughey\nOn: 26-Oct-2022 10:52:47\nNew Balance: $999970000.00'),
(18, 'O02620', 'Received: $30000.00\nFrom Account: Q55379\nHolder: george Jumbe\nOn: 26-Oct-2022 10:52:47\nNew Balance: $96360.00'),
(19, 'E15966', 'Received: $100.00\nFrom Account: W14867\nHolder: Kudakwashe Henry Mushunje\nOn: 10-Nov-2022 15:44:28\nNew Balance: $4100.00'),
(20, 'E15966', 'Deposited: $1200.00\nOn: 10-Nov-2022 15:50:17\nNew Balance: $5300.00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_pk`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
