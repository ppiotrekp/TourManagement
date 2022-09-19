insert into bus (brand, model, passengers_limit, equipment) values ('Mercedes', 'Tourismo', 20, 'toilet');
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

insert into passenger (schedule_id, first_name, last_name, email, phone_number) values
    (1, 'Piotr', 'Pyrczak', 'ppyrczak1@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, email, phone_number) values
    (1, 'Piotr', 'Pyrczak', 'ppyrczak2@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, email, phone_number) values
    (1, 'Piotr', 'Pyrczak', 'ppyrczak3@gmail.com', '888111222');
insert into passenger (schedule_id, first_name, last_name, email, phone_number) values
    (1, 'Piotr', 'Pyrczak', 'ppyrczak4@gmail.com', '888111222');


