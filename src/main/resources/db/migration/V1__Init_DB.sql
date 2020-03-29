create sequence hibernate_sequence start 1 increment 1

create table  currency if not exist (
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
