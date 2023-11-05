INSERT INTO tb_vehicles(manufacturer, model, plate) VALUES ('Ferrari', 'Ferrari 288GTO', 'ABC1D23');
INSERT INTO tb_vehicles(manufacturer, model, plate) VALUES ('Ferrari', 'Ferrari 289GTO', 'ABC1D24');



INSERT INTO tb_price(hour_price) VALUES (7.00);

INSERT INTO tb_payment_method (user_id, type, card_number, card_holder, cvv, card_expiration_date)  VALUES(1,'Crédito', '1234567890123456', 'teste', '123', '09/24')


INSERT INTO tb_users(name, email, telephone)  VALUES('jonh1', 'jonh1@gmail.com', '999129912');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh2', 'jonh2@gmail.com', '999229922');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh3', 'jonh3@gmail.com', '999329932');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh4', 'jonh4@gmail.com', '999429942');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh5', 'jonh5@gmail.com', '999529952');
INSERT INTO tb_users(name, email, telephone)  VALUES('jonh6', 'jonh6@gmail.com', '999629962');


INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (1,2);
INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (2,1);
INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (3,1);
INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (4,1);
INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (5,1);
INSERT INTO user_vehicle (user_id, vehicle_id) VALUES (6,1);


INSERT INTO tb_tickets(vehicle_id, user_id, check_in, check_out, payment_type, card_id, status, price) VALUES (1, 1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T23:10:01.176581600Z', 0 , 1 ,  'closed', 91.00);
INSERT INTO tb_tickets(vehicle_id, user_id, check_in, check_out, payment_type, card_id, status, price) VALUES (1, 1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T12:10:01.176581600Z',  0 , 1 , 'closed', 1.00);
INSERT INTO tb_tickets(vehicle_id, user_id, check_in, check_out, payment_type, card_id, status, price) VALUES (1, 1, '2023-09-24T17:17:01.176581600Z', '2023-09-24T23:10:01.176581600Z',  0 , 1 ,'closed', 42.00);
INSERT INTO tb_tickets(vehicle_id, user_id, check_in, check_out, payment_type, card_id, status, price) VALUES (1, 1, '2023-09-24T17:10:01.176581600Z', '2023-09-24T17:12:01.176581600Z', 1,  null, 'open', 0.00);
INSERT INTO tb_tickets(vehicle_id, user_id, check_in, check_out, payment_type, card_id, status, price) VALUES (1, 1, '2023-09-24T10:10:01.176581600Z', null, 1,  null, 'open', 0.00);
