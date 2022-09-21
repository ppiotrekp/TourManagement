insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo', 3, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 1', 5, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 2', 40, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 3', 110, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 4', 20, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 5', 20, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 6', 10, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 7', 40, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 8', 110, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 9', 20, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 10' , 20, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo 11', 10, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Opel', 'Vivaro', 40, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Bmw', 'Tourismo 13', 110, 'toilet');
insert into bus (brand, model, passengers_limit, equipment) values ('Audi', 'Tourismo 14', 20, 'toilet');


insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
                    (2, 'Krakow', 'Gdansk', '2022-01-01 00:00:01','2022-01-02 00:00:01',100);
insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
    (1, 'Krakow', 'Gdansk', '2022-01-01 00:00:01','2022-01-01 02:00:01',100);
insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
    (2, 'Krakow', 'Warszawa', '2022-01-01 00:00:01','2022-01-02 00:00:01',100);
insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
    (1, 'Krakow', 'Madryt', '2022-02-03 00:00:01','2022-02-07 02:00:01',100);
insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
    (2, 'Krakow', 'Barcelona', '2022-02-03 00:00:01','2022-09-02 00:00:01',100);
insert into schedule (bus_id, departure_from, departure_to, departure, arrival, ticket_price ) values
    (1, 'Krakow', 'Londyn', '2022-04-01 00:00:01','2022-06-01 02:00:01',100);


insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyrczak', 1, 'ppyrczak1@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyr', 1, 'ppyrczak2@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'kasia', 1, 'ppyrczak3@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'kamila', 1, 'ppyrczak4@gmail.com', '884211222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyrrqwk', 1, 'ppyrczak1@gmail.com', '855111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyr', 1, 'ppyrczak2@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyrqwcrwzak', 1, 'ppyrczak3@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (1, 'Piotr', 'Pyrczfrwrwak', 1, 'ppyrczak4@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (5, 'Piotr', 'Pyrczatk', 1, 'ppyrczak1@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (4, 'Piotr', 'Pyr', 1, 'ppyrczak2@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (3, 'Piotr', 'Pyrqwczak', 1, 'ppyrczak3@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, number_of_seats, email, phone_number) values
    (2, 'Piotr', 'Pyrczfrwak', 1, 'ppyrczak4@gmail.com', '888111222');


insert into review (schedule_id, passenger_id, rating, description) values (
    1, 1, 3, 'good');
insert into review (schedule_id, passenger_id, rating, description) values (
    1, 2, 4, 'very good');


