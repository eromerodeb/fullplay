# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id                        integer not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_product primary key (id))
;

create table supply (
  id                        integer not null,
  name                      varchar(255),
  stock                     double,
  constraint pk_supply primary key (id))
;


create table product_supply (
  product_id                     integer not null,
  supply_id                      integer not null,
  constraint pk_product_supply primary key (product_id, supply_id))
;
create sequence product_seq;

create sequence supply_seq;




alter table product_supply add constraint fk_product_supply_product_01 foreign key (product_id) references product (id) on delete restrict on update restrict;

alter table product_supply add constraint fk_product_supply_supply_02 foreign key (supply_id) references supply (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists product;

drop table if exists product_supply;

drop table if exists supply;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists product_seq;

drop sequence if exists supply_seq;

