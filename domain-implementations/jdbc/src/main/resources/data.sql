-- Insert Authors
INSERT INTO AUTHOR (FIRSTNAME, LASTNAME, BIOGRAPHY, BIRTHDATE, COUNTRY) VALUES
                                                                            ('John', 'Doe', 'John is an experienced writer...', '1980-05-10', 'USA'),
                                                                            ('Jane', 'Smith', 'Jane has been writing books...', '1975-07-20', 'Canada'),
                                                                            ('Emily', 'Johnson', 'Emily is known for her thrilling novels...', '1990-11-30', 'UK');

-- Insert Users
INSERT INTO USER (FIRSTNAME, LASTNAME, EMAIL, PASSWORDHASH, ADDRESS, CITY, COUNTRY, POSTALCODE, JOINDATE) VALUES
                                                                                                              ('Alice', 'Brown', 'alice.brown@example.com', 'hashed_password1', '123 Main St', 'New York', 'USA', '10001', CURRENT_TIMESTAMP),
                                                                                                              ('Bob', 'Wilson', 'bob.wilson@example.com', 'hashed_password2', '456 Elm St', 'Los Angeles', 'USA', '90001', CURRENT_TIMESTAMP),
                                                                                                              ('Carol', 'Davis', 'carol.davis@example.com', 'hashed_password3', '789 Pine St', 'Chicago', 'USA', '60001', CURRENT_TIMESTAMP);

-- Insert Books
INSERT INTO BOOK (TITLE, AUTHORID, ISBN, PRICE, GENRE, PUBLISHDATE, PUBLISHER, PAGECOUNT) VALUES
                                                                                              ('The Great Adventure', 1, '123-4567890123', 19.99, 'Adventure', '2021-06-15', 'Adventure Press', 300),
                                                                                              ('Mystery of the Ancients', 2, '456-1237894560', 24.99, 'Mystery', '2022-07-20', 'Mystery House', 250),
                                                                                              ('Romance in Paris', 3, '789-4561237890', 14.99, 'Romance', '2023-08-25', 'Romance Pub', 200);

-- Insert Orders (Note the backticks for ORDERS table)
INSERT INTO `ORDERS` (USERID, ORDERDATE, TOTALPRICE, SHIPPINGADDRESS, ORDERSTATUS) VALUES
                                                                                       (1, CURRENT_TIMESTAMP, 39.98, '123 Main St, New York, USA', 'Shipped'),
                                                                                       (2, CURRENT_TIMESTAMP, 24.99, '456 Elm St, Los Angeles, USA', 'Processing'),
                                                                                       (3, CURRENT_TIMESTAMP, 14.99, '789 Pine St, Chicago, USA', 'Delivered');

-- Insert Order Details
INSERT INTO ORDERDETAIL (ORDERID, BOOKID, QUANTITY, PRICEPERITEM) VALUES
                                                                      (1, 1, 2, 19.99),
                                                                      (2, 2, 1, 24.99),
                                                                      (3, 3, 1, 14.99);

-- Insert Reviews
INSERT INTO REVIEW (BOOKID, USERID, RATING, COMMENT, REVIEWDATE) VALUES
                                                                     (1, 1, 5, 'Amazing book with thrilling adventure!', CURRENT_TIMESTAMP),
                                                                     (2, 2, 4, 'Really enjoyed the mystery elements.', CURRENT_TIMESTAMP),
                                                                     (3, 3, 3, 'Good read, but the romance was a bit predictable.', CURRENT_TIMESTAMP);