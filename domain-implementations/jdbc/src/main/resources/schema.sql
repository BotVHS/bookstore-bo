DROP DATABASE IF EXISTS BOOKSTOREDB;
CREATE DATABASE BOOKSTOREDB;
USE BOOKSTOREDB;

CREATE TABLE USER (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      FIRSTNAME VARCHAR(50),
                      LASTNAME VARCHAR(50),
                      EMAIL VARCHAR(100) UNIQUE,
                      PASSWORDHASH VARCHAR(255),
                      ADDRESS VARCHAR(255),
                      CITY VARCHAR(50),
                      COUNTRY VARCHAR(50),
                      POSTALCODE VARCHAR(20),
                      JOINDATE DATETIME
);

CREATE TABLE AUTHOR (
                        ID INT AUTO_INCREMENT PRIMARY KEY,
                        FIRSTNAME VARCHAR(50),
                        LASTNAME VARCHAR(50),
                        BIOGRAPHY TEXT,
                        BIRTHDATE DATE,
                        COUNTRY VARCHAR(50)
);

CREATE TABLE BOOK (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      TITLE VARCHAR(100),
                      AUTHORID INT,
                      ISBN VARCHAR(20) UNIQUE,
                      PRICE DECIMAL(10, 2),
                      GENRE VARCHAR(50),
                      PUBLISHDATE DATE,
                      PUBLISHER VARCHAR(100),
                      PAGECOUNT INT,
                      FOREIGN KEY (AUTHORID) REFERENCES AUTHOR(ID)
);

CREATE TABLE ORDERS (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       USERID INT,
                       ORDERDATE DATETIME,
                       TOTALPRICE DECIMAL(10, 2),
                       SHIPPINGADDRESS VARCHAR(255),
                       ORDERSTATUS VARCHAR(50),
                       FOREIGN KEY (USERID) REFERENCES USER(ID)
);

CREATE TABLE ORDERDETAIL (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             ORDERID INT,
                             BOOKID INT,
                             QUANTITY INT,
                             PRICEPERITEM DECIMAL(10, 2),
                             FOREIGN KEY (ORDERID) REFERENCES ORDERS(ID),
                             FOREIGN KEY (BOOKID) REFERENCES BOOK(ID)
);

CREATE TABLE REVIEW (
                        ID INT AUTO_INCREMENT PRIMARY KEY,
                        BOOKID INT,
                        USERID INT,
                        RATING INT,
                        COMMENT TEXT,
                        REVIEWDATE DATETIME,
                        FOREIGN KEY (BOOKID) REFERENCES BOOK(ID),
                        FOREIGN KEY (USERID) REFERENCES USER(ID)
);
