CREATE TABLE employee
(
     id         bigint identity not null,
     name       varchar(255)    not null,
     surname    varchar(255)    not null,
     grade      bigint identity not null,
     salary     bigint identity not null,
     primary key (id)
);