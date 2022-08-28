create table bus (
    id bigint auto_increment primary key,
    brand varchar(50) not null,
    model varchar(50) not null,
    passengers_limit int not null,
    equipment varchar(50) null
);

