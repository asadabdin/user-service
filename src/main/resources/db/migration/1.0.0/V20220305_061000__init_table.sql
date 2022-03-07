-- Schema
CREATE SCHEMA IF NOT EXISTS users;

-- sequence
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Tables
CREATE TABLE IF NOT EXISTS users.user (
  id              INTEGER    NOT NULL PRIMARY KEY,
  firstname       VARCHAR    not null,
  surname         VARCHAR    not null,
  position        VARCHAR    not null,
  git_hub_url     VARCHAR    not null,
  created_on      TIMESTAMP  not null default now(),
  updated_on      TIMESTAMP  not null default now()
);
