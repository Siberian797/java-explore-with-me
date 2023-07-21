drop table if exists compilations;
drop table if exists requests;
drop table if exists compilation_events;
drop table if exists comments;
drop table if exists events;
drop table if exists users;
drop table if exists categories;
drop table if exists locations;

create table if not exists users (
    id    serial primary key,
    email varchar(254) not null unique,
    name  varchar(250) not null);

create table if not exists compilations (
    id     serial primary key,
    title  varchar(50) not null,
    pinned boolean      not null);

create table if not exists categories (
    id   serial primary key,
    name varchar(50) not null unique);

create table if not exists locations (
    id  serial primary key,
    lat float not null unique,
    lon float not null);

create table if not exists events (
    id                 serial primary key,
    annotation         varchar(2000)               not null,
    category_id        bigint                      not null,
    created_on         timestamp without time zone not null,
    description        varchar(7000)               not null,
    event_date         timestamp without time zone not null,
    initiator_id       bigint                      not null,
    location_id        bigint                      not null,
    paid               boolean                     not null,
    participant_limit  integer                     not null,
    published_on       timestamp without time zone,
    request_moderation boolean                     not null,
    state              varchar(50)                 not null,
    title              varchar(120)                not null,
    constraint ec_id_fk foreign key (category_id) references categories,
    constraint eu_id_fk foreign key (initiator_id) references users,
    constraint el_id_fk foreign key (location_id) references locations);

create table if not exists requests (
    id           serial primary key,
    event_id     bigint                      not null,
    requester_id bigint                      not null,
    status       varchar(50)                 not null,
    created      timestamp without time zone not null,
    constraint pu_id_fk foreign key (requester_id) references users,
    constraint pe_id_fk foreign key (event_id) references events);

create table if not exists compilation_events (
    compilation_id bigint not null,
    event_id       bigint not null,
    constraint ce_pk primary key (compilation_id, event_id),
    constraint cec_id_fk foreign key (compilation_id) references users,
    constraint cee_id_fk  foreign key (event_id) references events);

create table if not exists comments (
    id             serial primary key,
    event_id       bigint               not null,
    text           varchar(2000)        not null,
    commentator_id bigint               not null,
    constraint e_id_fk foreign key (event_id) references events,
    constraint c_id_fk foreign key (commentator_id) references users);