-- PHPMYADMIN SQL DUMP
-- VERSION 5.0.1
-- HTTPS://WWW.PHPMYADMIN.NET/
--
-- HOST: 127.0.0.1
-- GENERATION TIME: MAR 24, 2020 AT 06:55 PM
-- SERVER VERSION: 10.4.11-MARIADB
-- PHP VERSION: 7.4.3
DROP DATABASE IF EXISTS bookstoredb;

CREATE DATABASE bookstoredb;

USE bookstoredb;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET TIME_ZONE = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES UTF8MB4 */;

--
-- DATABASE: `bookstoredb`
--

-- --------------------------------------------------------

--
-- TABLE STRUCTURE FOR TABLE `author`
--

CREATE TABLE `author` (
  `AuthorID` int NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `Biography` text,
  `BirthDate` date DEFAULT NULL,
  `Country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`AuthorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
--
-- TABLE STRUCTURE FOR TABLE `user`
--

CREATE TABLE `user` (
                        `userID` int NOT NULL AUTO_INCREMENT,
                        `firstName` varchar(50) DEFAULT NULL,
                        `lastName` varchar(50) DEFAULT NULL,
                        `email` varchar(100) DEFAULT NULL,
                        `passwordHash` varchar(255) DEFAULT NULL,
                        `address` varchar(255) DEFAULT NULL,
                        `City` varchar(50) DEFAULT NULL,
                        `country` varchar(50) DEFAULT NULL,
                        `postalCode` varchar(20) DEFAULT NULL,
                        `joinDate` datetime DEFAULT NULL,
                        PRIMARY KEY (`userID`),
                        UNIQUE KEY `Email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- TABLE STRUCTURE FOR TABLE `book`
--

CREATE TABLE `book` (
    `bookID` int NOT NULL AUTO_INCREMENT,
    `title` varchar(100) DEFAULT NULL,
    `authorID` int DEFAULT NULL,
    `isbn` varchar(20) DEFAULT NULL,
    `price` decimal(10,2) DEFAULT NULL,
    `genre` varchar(50) DEFAULT NULL,
    `publishDate` date DEFAULT NULL,
    `publisher` varchar(100) DEFAULT NULL,
    `pageCount` int DEFAULT NULL,
    PRIMARY KEY (`bookID`),
    UNIQUE KEY `ISBN` (`isbn`),
    KEY `book_ibfk_1` (`authorID`),
    CONSTRAINT `book_ibfk_1` FOREIGN KEY (`authorID`) REFERENCES `author` (`AuthorID`)
) ENGINE=InnoDBDEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- TABLE STRUCTURE FOR TABLE `order`
--

CREATE TABLE `order` (
 `orderID` int NOT NULL AUTO_INCREMENT,
 `userID` int DEFAULT NULL,
 `orderDate` datetime DEFAULT NULL,
 `totalPrice` decimal(10,2) DEFAULT NULL,
 `shippingAddress` varchar(255) DEFAULT NULL,
 `orderStatus` varchar(50) DEFAULT NULL,
 PRIMARY KEY (`orderID`),
 KEY `orders_ibfk_1` (`userID`),
 CONSTRAINT `order_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- TABLE STRUCTURE FOR TABLE `orderdetail`
--

CREATE TABLE `orderdetail` (
   `orderDetailID` int NOT NULL AUTO_INCREMENT,
   `orderID` int DEFAULT NULL,
   `bookID` int DEFAULT NULL,
   `quantity` int DEFAULT NULL,
   `pricePerItem` decimal(10,2) DEFAULT NULL,
   PRIMARY KEY (`orderDetailID`),
   KEY `OrderID` (`orderID`),
   KEY `BookID` (`bookID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- TABLE STRUCTURE FOR TABLE `review`
--

CREATE TABLE `review` (
  `reviewID` int NOT NULL AUTO_INCREMENT,
  `bookID` int DEFAULT NULL,
  `userID` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `reviewDate` datetime DEFAULT NULL,
  PRIMARY KEY (`reviewID`),
  KEY `BookID` (`bookID`),
  KEY `UserID` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
