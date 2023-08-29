use tick3t;

INSERT INTO TCK_USER_M (user_name, user_birth, user_email, user_pwd, fan_id, fan_cd, create_dtm, user_status_cd, `role`)
VALUES
    ('User1', '1990-01-01', 'user1@example.com', 'password1', 'FANID001', 'FANCD001', NOW(), 'E', 'ROLE_USER'),
    ('User2', '1985-05-15', 'user2@example.com', 'password2', 'FANID002', 'FANCD002', NOW(), 'E', 'ROLE_USER'),
    ('User3', '1998-09-30', 'user3@example.com', 'password3', 'FANID003', 'FANCD003', NOW(), 'E', 'ROLE_USER'),
    ('User4', '1992-03-25', 'user4@example.com', 'password4', 'FANID004', 'FANCD004', NOW(), 'E', 'ROLE_USER'),
    ('User5', '1987-12-10', 'user5@example.com', 'password5', 'FANID005', 'FANCD005', NOW(), 'E', 'ROLE_USER'),
    ('User6', '2000-06-20', 'user6@example.com', 'password6', 'FANID006', 'FANCD006', NOW(), 'E', 'ROLE_USER'),
    ('User7', '1984-11-18', 'user7@example.com', 'password7', 'FANID007', 'FANCD007', NOW(), 'E', 'ROLE_USER'),
    ('User8', '1995-08-02', 'user8@example.com', 'password8', 'FANID008', 'FANCD008', NOW(), 'E', 'ROLE_USER'),
    ('User9', '2002-04-05', 'user9@example.com', 'password9', 'FANID009', 'FANCD009', NOW(), 'E', 'ROLE_USER'),
    ('Use10', '1989-07-12', 'user10@example.com', 'password10', 'FANID010', 'FANCD010', NOW(), 'E', 'ROLE_USER');

INSERT INTO TCK_USER_M (user_name, user_birth, user_email, user_pwd, fan_id, fan_cd, create_dtm, user_status_cd, `role`)
VALUES
    ('Use11', '1993-02-14', 'user11@example.com', 'password11', 'FANID011', 'FANCD011', NOW(), 'E', 'ROLE_USER'),
    ('Use12', '1986-09-22', 'user12@example.com', 'password12', 'FANID012', 'FANCD012', NOW(), 'E', 'ROLE_USER'),
    ('Use13', '1997-11-03', 'user13@example.com', 'password13', 'FANID013', 'FANCD013', NOW(), 'E', 'ROLE_USER'),
    ('Use14', '2001-06-08', 'user14@example.com', 'password14', 'FANID014', 'FANCD014', NOW(), 'E', 'ROLE_USER'),
    ('Use15', '1988-04-16', 'user15@example.com', 'password15', 'FANID015', 'FANCD015', NOW(), 'E', 'ROLE_USER'),
    ('Use16', '1994-12-30', 'user16@example.com', 'password16', 'FANID016', 'FANCD016', NOW(), 'E', 'ROLE_USER'),
    ('Usr17', '2003-08-11', 'user17@example.com', 'password17', 'FANID017', 'FANCD017', NOW(), 'E', 'ROLE_USER'),
    ('Usr18', '1991-03-26', 'user18@example.com', 'password18', 'FANID018', 'FANCD018', NOW(), 'E', 'ROLE_USER'),
    ('Usr19', '1983-07-05', 'user19@example.com', 'password19', 'FANID019', 'FANCD019', NOW(), 'E', 'ROLE_USER'),
    ('Usr20', '2000-10-19', 'user20@example.com', 'password20', 'FANID020', 'FANCD020', NOW(), 'E', 'ROLE_USER');

 
   INSERT INTO TCK_HALL_M (hall_name, hall_loc)
VALUES
    ('Hall A', '123 Main St, City A'),
    ('Hall B', '456 Park Ave, City B'),
    ('Hall C', '789 Elm Rd, City C'),
    ('Hall D', '234 Oak Ln, City D'),
    ('Hall E', '567 Pine St, City E'),
    ('Hall F', '890 Maple Rd, City F'),
    ('Hall G', '123 Cedar Ave, City G'),
    ('Hall H', '456 Birch St, City H'),
    ('Hall I', '789 Willow Rd, City I'),
    ('Hall J', '234 Elm St, City J');
   
   INSERT INTO TCK_HALL_M (hall_name, hall_loc)
VALUES
    ('Hall K', '123 Pine St, City K'),
    ('Hall L', '456 Maple Ave, City L'),
    ('Hall M', '789 Cedar Rd, City M'),
    ('Hall N', '234 Birch St, City N'),
    ('Hall O', '567 Willow Ave, City O'),
    ('Hall P', '890 Elm St, City P'),
    ('Hall Q', '123 Oak Rd, City Q'),
    ('Hall R', '456 Pine St, City R'),
    ('Hall S', '789 Cedar Ave, City S'),
    ('Hall T', '234 Birch Rd, City T');   
   
   INSERT INTO TCK_CONCERT_M (hall_id, cncr_title, cncr_img_url, cncr_dtm, cncr_price, cncr_cancel_date, fan_cd, cncr_start_dtm)
VALUES
    (1, 'Concert 1', 'image1.jpg', '2023-09-15 18:00:00', 100, '2023-09-14', 1, '2023-09-15 19:00:00'),
    (2, 'Concert 2', 'image2.jpg', '2023-09-20 20:00:00', 150, '2023-09-19', 2, '2023-09-20 21:00:00'),
    (1, 'Concert 3', 'image3.jpg', '2023-09-25 15:00:00', 80, '2023-09-24', 1, '2023-09-25 16:00:00'),
    (2, 'Concert 4', 'image4.jpg', '2023-10-05 19:30:00', 120, '2023-10-04', 2, '2023-10-05 20:30:00'),
    (1, 'Concert 5', 'image5.jpg', '2023-10-10 14:00:00', 90, '2023-10-09', 1, '2023-10-10 15:00:00'),
    (2, 'Concert 6', 'image6.jpg', '2023-10-15 17:30:00', 130, '2023-10-14', 2, '2023-10-15 18:30:00'),
    (1, 'Concert 7', 'image7.jpg', '2023-10-20 21:00:00', 110, '2023-10-19', 1, '2023-10-20 22:00:00'),
    (2, 'Concert 8', 'image8.jpg', '2023-10-25 16:00:00', 140, '2023-10-24', 2, '2023-10-25 17:00:00'),
    (1, 'Concert 9', 'image9.jpg', '2023-11-05 18:30:00', 95, '2023-11-04', 1, '2023-11-05 19:30:00'),
    (2, 'Concert 10', 'image10.jpg', '2023-11-10 13:00:00', 160, '2023-11-09', 2, '2023-11-10 14:00:00');

   
INSERT INTO TCK_CONCERT_M (hall_id, cncr_title, cncr_img_url, cncr_dtm, cncr_price, cncr_cancel_date, fan_cd, cncr_start_dtm)
VALUES
    (1, 'Concert 11', 'image11.jpg', '2023-11-15 18:00:00', 110, '2023-11-14', 1, '2023-11-15 19:00:00'),
    (2, 'Concert 12', 'image12.jpg', '2023-11-20 20:00:00', 170, '2023-11-19', 2, '2023-11-20 21:00:00'),
    (1, 'Concert 13', 'image13.jpg', '2023-11-25 15:00:00', 90, '2023-11-24', 1, '2023-11-25 16:00:00'),
    (2, 'Concert 14', 'image14.jpg', '2023-12-05 19:30:00', 130, '2023-12-04', 2, '2023-12-05 20:30:00'),
    (1, 'Concert 15', 'image15.jpg', '2023-12-10 14:00:00', 100, '2023-12-09', 1, '2023-12-10 15:00:00'),
    (2, 'Concert 16', 'image16.jpg', '2023-12-15 17:30:00', 150, '2023-12-14', 2, '2023-12-15 18:30:00'),
    (1, 'Concert 17', 'image17.jpg', '2023-12-20 21:00:00', 120, '2023-12-19', 1, '2023-12-20 22:00:00'),
    (2, 'Concert 18', 'image18.jpg', '2023-12-25 16:00:00', 160, '2023-12-24', 2, '2023-12-25 17:00:00'),
    (1, 'Concert 19', 'image19.jpg', '2024-01-05 18:30:00', 105, '2024-01-04', 1, '2024-01-05 19:30:00'),
    (2, 'Concert 20', 'image20.jpg', '2024-01-10 13:00:00', 180, '2024-01-09', 2, '2024-01-10 14:00:00');
   
   
  

INSERT INTO TCK_HALL_SEAT_M (hall_id, grade_name, seat_no)
VALUES
    (1, 'Standard', 101),
    (2, 'VIP', 201),
    (1, 'Standard', 102),
    (2, 'VIP', 202),
    (1, 'Standard', 103),
    (2, 'VIP', 203),
    (1, 'Standard', 104),
    (2, 'VIP', 204),
    (1, 'Standard', 105),
    (2, 'VIP', 205);

   INSERT INTO TCK_LOG_M (user_id, access_ip, access_status_cd, create_dtm)
VALUES
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '0', NOW()),
    (3, '192.168.0.3', '2', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW()),
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '1', NOW()),
    (3, '192.168.0.3', '0', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW());
   
   INSERT INTO TCK_LOG_M (user_id, access_ip, access_status_cd, create_dtm)
VALUES
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '0', NOW()),
    (3, '192.168.0.3', '2', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW()),
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '1', NOW()),
    (3, '192.168.0.3', '0', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW()),
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '1', NOW()),
    (3, '192.168.0.3', '0', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW()),
    (1, '192.168.0.1', '0', NOW()),
    (2, '192.168.0.2', '1', NOW()),
    (3, '192.168.0.3', '0', NOW()),
    (4, '192.168.0.4', '0', NOW()),
    (5, '192.168.0.5', '0', NOW());

