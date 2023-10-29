INSERT INTO tb_vehicles(manufacturer, model, plate) VALUES ('Ferrari', 'Ferrari 288GTO', 'ABC1D23');

INSERT INTO tb_tickets(vehicle_id, check_in, check_out, status, price) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T23:10:01.176581600Z', 'closed', 91.00);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out, status, price) VALUES (1, '2023-09-24T10:10:01.176581600Z', null, 'open', 0.00);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out, status, price) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T12:10:01.176581600Z', 'closed', 1.00);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out, status, price) VALUES (1, '2023-09-24T17:17:01.176581600Z', '2023-09-24T23:10:01.176581600Z', 'closed', 42.00);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out, status, price) VALUES (1, '2023-09-24T17:10:01.176581600Z', null, 'open', 0.00);

INSERT INTO tb_price(hour_price) VALUES (7.00);

INSERT INTO tb_users(name, email, telephone)  VALUES('jonh1', 'jonh1@gmail.com', '999129912');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh2', 'jonh2@gmail.com', '999229922');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh3', 'jonh3@gmail.com', '999329932');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh4', 'jonh4@gmail.com', '999429942');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh5', 'jonh5@gmail.com', '999529952');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh6', 'jonh6@gmail.com', '999629962');
