-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 14, 2020 at 05:26 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `amorepos`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `menuid` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sellprice` int(11) NOT NULL,
  `ingredientprice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`menuid`, `name`, `sellprice`, `ingredientprice`) VALUES
('60xVKofTc5', 'Hainam Rice', 6000, 4000),
('jf9021uhj9', 'Fried Chicken', 16000, 12000);

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `transactionid` varchar(10) NOT NULL,
  `menuid` varchar(10) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`transactionid`, `menuid`, `quantity`) VALUES
('Fj82dcx3Sh', '60xVKofTc5', 2),
('Fj82dcx3Sh', 'jf9021uhj9', 3),
('XRUOtH7Mh2', 'jf9021uhj9', 3),
('zplYUzBgD3', 'jf9021uhj9', 9);

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `transactionid` varchar(10) NOT NULL,
  `staffid` varchar(10) NOT NULL,
  `transactiondate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`transactionid`, `staffid`, `transactiondate`) VALUES
('Fj82dcx3Sh', '19uhsU2HJA', '2020-05-14'),
('PYQqkVjmpf', '19uhsU2HJA', '2020-05-14'),
('XRUOtH7Mh2', '19uhsU2HJA', '2020-05-14'),
('zplYUzBgD3', '19uhsU2HJA', '2020-05-14');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userid` varchar(20) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `role` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `fullname`, `role`, `email`, `password`) VALUES
('19uhsU2HJA', 'admin123', 'Admin', 'admin@gmail.com', 'admin123'),
('IjgAWCz4gl', 'accountant123', 'Accountant', 'accountant@gmail.com', 'accountant123'),
('wIcq7MnWa0', 'cashier123', 'Cashier', 'cashier@gmail.com', 'cashier');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menuid`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`transactionid`,`menuid`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`transactionid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
