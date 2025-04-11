CREATE DATABASE db_Trial;
use db_Trial;

INSERT INTO User (id, name, email, password, active) VALUES
(1, 'dur', 'dur@gmail.com', 'password123', true),
(2, 'ali', 'ali@gmail.com', 'pass123', true),
(3, 'siraj', 'siraj@gmail.com', 'siraj123', false);

INSERT INTO Device (id, device_id, device_type , os, metatag, user_id) VALUES
(1, 'DEVICE12345', 'Smartphone', 'Android', 'Vivo Y21', 1),
(2, 'DEVICE234', 'Apple', 'iOS', 'iphone 16', 2),
(3, 'DEVICE54321', 'Smartphone', 'Android', 'Google Pixel 9', 1);

INSERT INTO Esim (id, iccid, imsi, activation_code, eid, device_id) VALUES
(1, '89441234567890', '310150123456789', 'ACTIVATE123', 'EID12345678', 1),
(2, '89449876543210', '310150987654321', 'ACTIVATE456', 'EID87654321', 2),
(3, '89445678901234', '310150567890123', 'ACTIVATE789', 'EID43218765', 1);

SHOW TABLES;
select * from User;
drop database db_Trial;
