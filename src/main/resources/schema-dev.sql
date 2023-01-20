DROP DATABASE IF EXISTS book_store;
CREATE DATABASE book_store;

USE book_store;

DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id          bigint       not null auto_increment primary key,
    first_name  varchar(255) not null,
    second_name varchar(255) not null,
    birthday    date         not null
) engine = INNODB;

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id               bigint       not null auto_increment primary key,
    name             varchar(255) not null,
    description      varchar(255),
    publication_date date         not null,
    author_id        bigint,
    foreign key (author_id) references author (id) on DELETE cascade
) ENGINE = INNODB;

CREATE INDEX book_name ON book (name);
CREATE INDEX book_publicationDate ON book (publication_date);
CREATE INDEX book_name_and_publicationDate ON book (name, publication_date);
