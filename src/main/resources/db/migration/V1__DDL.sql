SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.authority (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE public.role (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE public.role_authority (
    role_id character varying(255) NOT NULL,
    authority_id character varying(255) NOT NULL
);

CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    account_expiration_date date,
    credentials_expiration_date date,
    email character varying(255) NOT NULL,
    enabled integer,
    first_name character varying(255),
    last_name character varying(255),
    locked integer,
    password character varying(255)
);

CREATE TABLE public.users_role (
    users_id character varying(255) NOT NULL,
    role_id character varying(255) NOT NULL
);

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_pkey PRIMARY KEY (role_id, authority_id);


ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.users_role
    ADD CONSTRAINT users_role_pkey PRIMARY KEY (users_id, role_id);


ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT fk2052966dco7y9f97s1a824bj1 FOREIGN KEY (role_id) REFERENCES public.role(id) ON DELETE CASCADE;


ALTER TABLE ONLY public.users_role
    ADD CONSTRAINT fk3qjq7qsiigxa82jgk0i0wuq3g FOREIGN KEY (role_id) REFERENCES public.role(id);


ALTER TABLE ONLY public.users_role
    ADD CONSTRAINT fkiu0xsee0dmwa28nffgyf4bcvc FOREIGN KEY (users_id) REFERENCES public.users(id);


ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT fkqbri833f7xop13bvdje3xxtnw FOREIGN KEY (authority_id) REFERENCES public.authority(id);