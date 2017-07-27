# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id                        integer not null,
  name                      varchar(255),
  description               text,
  constraint pk_product primary key (id))
;

create table sale (
  id                        integer not null,
  buyer                     varchar(255),
  product_id                integer,
  amount                    integer,
  constraint pk_sale primary key (id))
;

create table supply (
  id                        integer not null,
  name                      varchar(255),
  stock                     double,
  constraint pk_supply primary key (id))
;

create table user (
  id                        integer not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint uq_user_username unique (username),
  constraint pk_user primary key (id))
;


create table product_supply (
  product_id                     integer not null,
  supply_id                      integer not null,
  constraint pk_product_supply primary key (product_id, supply_id))
;
create sequence product_seq;

create sequence sale_seq;

create sequence supply_seq;

create sequence user_seq;

alter table sale add constraint fk_sale_product_1 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_sale_product_1 on sale (product_id);



alter table product_supply add constraint fk_product_supply_product_01 foreign key (product_id) references product (id) on delete restrict on update restrict;

alter table product_supply add constraint fk_product_supply_supply_02 foreign key (supply_id) references supply (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists product;

drop table if exists product_supply;

drop table if exists sale;

drop table if exists supply;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists product_seq;

drop sequence if exists sale_seq;

drop sequence if exists supply_seq;

drop sequence if exists user_seq;

