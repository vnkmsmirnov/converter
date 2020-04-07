create sequence hibernate_sequence start 1 increment 1;

create table if not exists currency (
  id serial,
  idvalute varchar (20),
  numcode int8,
  charcode varchar(8),
  nominal int8,
  name varchar(255),
  value numeric,
  date timestamp,
  primary key (id)
);

insert into  currency (idvalute, numcode, charcode, nominal, name, value, date)
values ('RU0001', '0001', 'RUR', '1', 'Российский рубль', '1', '01.01.2020');