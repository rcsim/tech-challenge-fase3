
-- Create a new database
CREATE DATABASE  parkingmeter_db;

-- Connect to the newly created database
\c parkingmeter_db;


-- Drop tables if they exist with CASCADE
DROP TABLE IF EXISTS tb_payment_method CASCADE;
DROP TABLE IF EXISTS tb_price CASCADE;
DROP TABLE IF EXISTS tb_tickets CASCADE;
DROP TABLE IF EXISTS tb_users CASCADE;
DROP TABLE IF EXISTS tb_vehicles CASCADE;
DROP TABLE IF EXISTS user_vehicle CASCADE;



-- Create the "tb_payment_method" table
CREATE TABLE tb_payment_method (
                                   id SERIAL PRIMARY KEY,
                                   user_id BIGINT,
                                   card_expiration_date VARCHAR(255),
                                   card_holder VARCHAR(255),
                                   card_number VARCHAR(255),
                                   cvv VARCHAR(255),
                                   type VARCHAR(255)
);

-- Create the "tb_price" table
CREATE TABLE tb_price (
                          id SERIAL PRIMARY KEY,
                          hour_price FLOAT
);

-- Create the "tb_tickets" table
CREATE TABLE tb_tickets (
                            id SERIAL PRIMARY KEY,
                            payment_type INTEGER NOT NULL,
                            price FLOAT,
                            card_id BIGINT,
                            check_in TIMESTAMPTZ,
                            check_out TIMESTAMPTZ,
                            user_id BIGINT,
                            vehicle_id BIGINT,
                            pix_code VARCHAR(255),
                            status VARCHAR(255)
);

-- Create the "tb_users" table
CREATE TABLE tb_users (
                          id SERIAL PRIMARY KEY,
                          email VARCHAR(255),
                          name VARCHAR(255),
                          telephone VARCHAR(255)
);

-- Create the "tb_vehicles" table
CREATE TABLE tb_vehicles (
                             id SERIAL PRIMARY KEY,
                             manufacturer VARCHAR(255),
                             model VARCHAR(255),
                             plate VARCHAR(255)
);

-- Create the "user_vehicle" table
CREATE TABLE user_vehicle (
                              user_id BIGINT NOT NULL,
                              vehicle_id BIGINT NOT NULL
);

-- Add foreign key constraints
ALTER TABLE tb_tickets
    ADD CONSTRAINT fk_card
        FOREIGN KEY (card_id)
            REFERENCES tb_payment_method (id);

ALTER TABLE tb_tickets
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES tb_users (id);

ALTER TABLE tb_tickets
    ADD CONSTRAINT fk_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES tb_vehicles (id);

ALTER TABLE user_vehicle
    ADD CONSTRAINT fk_vehicle
        FOREIGN KEY (vehicle_id)
            REFERENCES tb_vehicles (id);

ALTER TABLE user_vehicle
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES tb_users (id);