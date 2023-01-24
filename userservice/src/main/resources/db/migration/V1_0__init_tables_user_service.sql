CREATE TABLE IF NOT EXISTS user_service."user"
(
    uuid uuid NOT NULL,
    dt_create timestamp without time zone,
    dt_update timestamp without time zone,
    mail character varying(255) COLLATE pg_catalog."default",
    nick character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS user_service."user"
    OWNER to postgres;

INSERT INTO user_service."user"(
	uuid, dt_create, dt_update, mail, nick, password, role, status)
	VALUES ('13d89eba-daa6-4f47-8fad-369c2eaf0c96',
	 now(),
	 now(),
	 'admin@gmail.com',
	 'Admin',
	 '$2a$10$QraQ3FVyN4ugeineHknHf.gmyPSDuW6Wsd/i/X.GKOMLuIxat0ECm',
	 'ADMIN',
	 'ACTIVATED');