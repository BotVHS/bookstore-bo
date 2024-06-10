CREATE DATABASE BOOKSTOREDB;
USE BOOKSTOREDB;

CREATE TABLE USER (
                      USERID INT AUTO_INCREMENT PRIMARY KEY,
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
                        AUTHORID INT AUTO_INCREMENT PRIMARY KEY,
                        FIRSTNAME VARCHAR(50),
                        LASTNAME VARCHAR(50),
                        BIOGRAPHY TEXT,
                        BIRTHDATE DATE,
                        COUNTRY VARCHAR(50)
);

CREATE TABLE BOOK (
                      BOOKID INT AUTO_INCREMENT PRIMARY KEY,
                      TITLE VARCHAR(100),
                      AUTHORID INT,
                      ISBN VARCHAR(20) UNIQUE,
                      PRICE DECIMAL(10, 2),
                      GENRE VARCHAR(50),
                      PUBLISHDATE DATE,
                      PUBLISHER VARCHAR(100),
                      PAGECOUNT INT,
                      FOREIGN KEY (AUTHORID) REFERENCES AUTHOR(AUTHORID)
);

CREATE TABLE ORDER (
                       ORDERID INT AUTO_INCREMENT PRIMARY KEY,
                       USERID INT,
                       ORDERDATE DATETIME,
                       TOTALPRICE DECIMAL(10, 2),
                       SHIPPINGADDRESS VARCHAR(255),
                       ORDERSTATUS VARCHAR(50),
                       FOREIGN KEY (USERID) REFERENCES USER(USERID)
);

CREATE TABLE ORDERDETAIL (
                             ORDERDETAILID INT AUTO_INCREMENT PRIMARY KEY,
                             ORDERID INT,
                             BOOKID INT,
                             QUANTITY INT,
                             PRICEPERITEM DECIMAL(10, 2),
                             FOREIGN KEY (ORDERID) REFERENCES ORDER(ORDERID),
                             FOREIGN KEY (BOOKID) REFERENCES BOOK(BOOKID)
);

CREATE TABLE REVIEW (
                        REVIEWID INT AUTO_INCREMENT PRIMARY KEY,
                        BOOKID INT,
                        USERID INT,
                        RATING INT,
                        COMMENT TEXT,
                        REVIEWDATE DATETIME,
                        FOREIGN KEY (BOOKID) REFERENCES BOOK(BOOKID),
                        FOREIGN KEY (USERID) REFERENCES USER(USERID)
);
