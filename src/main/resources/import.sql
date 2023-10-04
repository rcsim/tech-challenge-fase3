INSERT INTO tb_vehicles(manufacturer, model, plate) VALUES ('Ferrari', 'Ferrari 288GTO', 'ABC1D23');

INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T23:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', null);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T12:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T17:17:01.176581600Z', '2023-09-24T23:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T17:10:01.176581600Z', null);

INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro1', 'cep1', 'logradouro1', 'MG');
INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro2', 'cep2', 'logradouro2', 'ES');
INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro3', 'cep3', 'logradouro3', 'SP');
INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro4', 'cep4', 'logradouro4', 'RJ');
INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro5', 'cep5', 'logradouro5', 'GO');
INSERT INTO tb_address(bairro, cep, logradouro, uf) VALUES('bairro6', 'cep6', 'logradouro6', 'MT');

INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh1', 'jonh1@gmail.com', '999129912', 1);
INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh2', 'jonh2@gmail.com', '999229922', 1);
INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh3', 'jonh3@gmail.com', '999329932', 1);
INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh4', 'jonh4@gmail.com', '999429942', 2);
INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh5', 'jonh5@gmail.com', '999529952', 3);
INSERT INTO tb_users(name, email, telephone, address_id)  VALUES('jonh6', 'jonh6@gmail.com', '999629962', 4);




