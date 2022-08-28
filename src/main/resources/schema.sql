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
    departure date not null,
    arrival date not null,
    ticket_price varchar(50) null
);

alter table schedule
    add constraint schedule_bus_id
    foreign key (bus_id) references bus(id)

