# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account (
  account_id                bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  constraint pk_account primary key (account_id))
;

create table account_group (
  account_group_id          bigint not null,
  creator_account_id        bigint,
  group_name                varchar(255),
  group_description         varchar(255),
  constraint pk_account_group primary key (account_group_id))
;

create table comment (
  comment_id                bigint not null,
  posted_at                 timestamp,
  content                   varchar(255),
  post_post_id              bigint,
  constraint pk_comment primary key (comment_id))
;

create table post (
  post_id                   bigint not null,
  title                     varchar(255),
  posted_at                 timestamp,
  content                   varchar(255),
  author_account_id         bigint,
  constraint pk_post primary key (post_id))
;


create table account_colleague (
  account_Id                     bigint not null,
  colleague_Id                   bigint not null,
  constraint pk_account_colleague primary key (account_Id, colleague_Id))
;

create table account_group_account (
  account_group_account_group_id bigint not null,
  account_account_id             bigint not null,
  constraint pk_account_group_account primary key (account_group_account_group_id, account_account_id))
;
create sequence account_seq;

create sequence account_group_seq;

create sequence comment_seq;

create sequence post_seq;

alter table account_group add constraint fk_account_group_creator_1 foreign key (creator_account_id) references account (account_id) on delete restrict on update restrict;
create index ix_account_group_creator_1 on account_group (creator_account_id);
alter table comment add constraint fk_comment_post_2 foreign key (post_post_id) references post (post_id) on delete restrict on update restrict;
create index ix_comment_post_2 on comment (post_post_id);
alter table post add constraint fk_post_author_3 foreign key (author_account_id) references account (account_id) on delete restrict on update restrict;
create index ix_post_author_3 on post (author_account_id);



alter table account_colleague add constraint fk_account_colleague_account_01 foreign key (account_Id) references account (account_id) on delete restrict on update restrict;

alter table account_colleague add constraint fk_account_colleague_account_02 foreign key (colleague_Id) references account (account_id) on delete restrict on update restrict;

alter table account_group_account add constraint fk_account_group_account_acco_01 foreign key (account_group_account_group_id) references account_group (account_group_id) on delete restrict on update restrict;

alter table account_group_account add constraint fk_account_group_account_acco_02 foreign key (account_account_id) references account (account_id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists account;

drop table if exists account_colleague;

drop table if exists account_group_account;

drop table if exists account_group;

drop table if exists comment;

drop table if exists post;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists account_seq;

drop sequence if exists account_group_seq;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

