INSERT INTO tb_vehicles(manufacturer, model, plate) VALUES ('Ferrari', 'Ferrari 288GTO', 'ABC1D23');

INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T23:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', null);
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T10:10:01.176581600Z', '2023-09-24T12:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T17:17:01.176581600Z', '2023-09-24T23:10:01.176581600Z');
INSERT INTO tb_tickets(vehicle_id, check_in, check_out) VALUES (1, '2023-09-24T17:10:01.176581600Z', null);