CREATE TABLE IF NOT EXISTS audit_service.audit
(
    uuid uuid NOT NULL,
    es_id character varying(255) COLLATE pg_catalog."default",
    text character varying(255) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default",
    user_dt_create timestamp without time zone,
    user_dt_update timestamp without time zone,
    user_mail character varying(255) COLLATE pg_catalog."default",
    user_nick character varying(255) COLLATE pg_catalog."default",
    user_role character varying(255) COLLATE pg_catalog."default",
    user_status character varying(255) COLLATE pg_catalog."default",
    user_uuid uuid,
    CONSTRAINT audit_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS audit_service.audit
    OWNER to postgres;