-- TCK_USER_M
INSERT INTO TCK_USER_M (user_name, user_birth, user_email, user_pwd, fan_id, fan_cd, role)
VALUES ('User1', '1990-01-01', 'user1@email.com', 'password', 'fan_0001', 'FAN01', 'ROLE_USER'),
       ('User2', '1991-02-02', 'user2@email.com', 'password', 'fan_0002', 'FAN02', 'ROLE_USER'),
       ('User3', '1992-03-03', 'user3@email.com', 'password', 'fan_0003', 'FAN03', 'ROLE_USER'),
       ('User4', '1993-04-04', 'user4@email.com', 'password', 'fan_0004', 'FAN04', 'ROLE_USER'),
       ('User5', '1994-05-05', 'user5@email.com', 'password', 'fan_0005', 'FAN05', 'ROLE_USER'),
       ('User6', '1995-06-06', 'user6@email.com', 'password', 'fan_0006', 'FAN06', 'ROLE_USER'),
       ('User7', '1996-07-07', 'user7@email.com', 'password', 'fan_0007', 'FAN07', 'ROLE_USER'),
       ('User8', '1997-08-08', 'user8@email.com', 'password', 'fan_0008', 'FAN08', 'ROLE_USER'),
       ('User9', '1998-09-09', 'user9@email.com', 'password', 'fan_0009', 'FAN09', 'ROLE_USER'),
       ('Use10', '1999-10-10', 'user10@email.com', 'password', 'fan_0010', 'FAN10', 'ROLE_USER');

-- TCK_HALL_M
INSERT INTO TCK_HALL_M (hall_name, hall_loc)
VALUES ('Hall1', 'Location1'),
       ('Hall2', 'Location2'),
       ('Hall3', 'Location3'),
       ('Hall4', 'Location4'),
       ('Hall5', 'Location5'),
       ('Hall6', 'Location6'),
       ('Hall7', 'Location7'),
       ('Hall8', 'Location8'),
       ('Hall9', 'Location9'),
       ('Hall10', 'Location10');


-- TCK_CONCERT_M
INSERT INTO TCK_CONCERT_M (hall_id, cncr_title, cncr_price)
VALUES (1, 'Concert1', 100),
       (2, 'Concert2', 150),
       (3, 'Concert3', 200),
       (4, 'Concert4', 250),
       (5, 'Concert5', 300),
       (6, 'Concert6', 350),
       (7, 'Concert7', 400),
       (8, 'Concert8', 450),
       (9, 'Concert9', 500),
       (10, 'Concert10', 550);

-- TCK_CONCERT_M
INSERT INTO TCK_CONCERT_M (hall_id, cncr_title, cncr_price)
VALUES (1, 'Concert1', 100),
       (2, 'Concert2', 150),
       (3, 'Concert3', 200),
       (4, 'Concert4', 250),
       (5, 'Concert5', 300),
       (6, 'Concert6', 350),
       (7, 'Concert7', 400),
       (8, 'Concert8', 450),
       (9, 'Concert9', 500),
       (10, 'Concert10', 550);

-- TCK_HALL_SEAT_M
INSERT INTO TCK_HALL_SEAT_M (hall_id, grade_name, seat_no)
VALUES (1, 'Grade1', 1),
       (1, 'Grade2', 2),
       (1, 'Grade3', 3),
       (1, 'Grade4', 4),
       (1, 'Grade5', 5),
       (1, 'Grade6', 6),
       (1, 'Grade7', 7),
       (1, 'Grade8', 8),
       (1, 'Grade9', 9),
       (1, 'Grade10', 10);

-- CNCR_TICKET_M
INSERT INTO CNCR_TICKET_M (customer_id, concert_id, grade_id, hall_id, ticket_price, seat_no)
VALUES (1, 11, 1, 1, 100, 1),
       (2, 12, 2, 2, 150, 2),
       (3, 13, 3, 3, 200, 3),
       (4, 14, 4, 4, 250, 4),
       (5, 15, 5, 5, 300, 5),
       (6, 16, 6, 1, 350, 6),
       (7, 17, 7, 2, 400, 7),
       (8, 18, 8, 3, 450, 8),
       (9, 19, 9, 3, 500, 9),
       (10, 20, 10, 5, 550, 10);

-- TCK_LOG_M
INSERT INTO TCK_LOG_M (user_id, access_ip, access_status_cd)
VALUES (1, '192.168.1.1', '0'),
       (2, '192.168.1.2', '0'),
       (3, '192.168.1.3', '0'),
       (4, '192.168.1.4', '0'),
       (5, '192.168.1.5', '0'),
       (6, '192.168.1.6', '0'),
       (7, '192.168.1.7', '0'),
       (8, '192.168.1.8', '0'),
       (9, '192.168.1.9', '0'),
       (10, '192.168.1.10', '0');
