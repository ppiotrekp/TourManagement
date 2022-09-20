create table bus (
    id bigint auto_increment primary key,
    brand varchar(50) not null,
    model varchar(50) not null,
    passengers_limit int not null,
    equipment varchar(50) null
);

create table schedule (
    id bigint auto_increment primary key,
    bus_id bigint not null,
    departure_from varchar(50) not null,
    departure_to varchar(50) not null,
    departure datetime null,
    arrival datetime null ,
    ticket_price varchar(50) null
);

create table passenger (
    id bigint auto_increment primary key,
    schedule_id bigint not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(20) not null unique,
    phone_number char(9) not null
);

create table review (
    id bigint auto_increment primary key,
    schedule_id bigint not null,
    passenger_id bigint not null,
    rating int not null,
    description varchar (100) not null
);

alter table schedule
    add constraint schedule_bus_id
    foreign key (bus_id) references bus(id);

alter table schedule
    add constraint date_constraint
    check (departure < arrival);

alter table passenger
    add constraint passenger_schedule_id
        foreign key (schedule_id) references schedule(id);

alter table review
    add constraint review_schedule_id
        foreign key (schedule_id) references schedule(id);

alter table review
    add constraint review_passenger_id
        foreign key (passenger_id) references passenger(id);




