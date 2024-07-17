-- Table: public.files

-- DROP TABLE IF EXISTS public.files;

CREATE TABLE IF NOT EXISTS public.files
(
    file_seq character varying(100) COLLATE pg_catalog."default" NOT NULL,
    file_id character varying(50) COLLATE pg_catalog."default" NOT NULL,
    file_name character varying(512) COLLATE pg_catalog."default",
    file_ext character varying(512) COLLATE pg_catalog."default",
    ins_id character varying(50) COLLATE pg_catalog."default",
    ins_date timestamp without time zone,
    CONSTRAINT files_pkey PRIMARY KEY (file_seq, file_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.files
    OWNER to studyproject;

COMMENT ON TABLE public.files
    IS '파일';

COMMENT ON COLUMN public.files.file_seq
    IS '파일번호';

COMMENT ON COLUMN public.files.file_id
    IS '파일ID';

COMMENT ON COLUMN public.files.file_name
    IS '원본파일명';

COMMENT ON COLUMN public.files.file_ext
    IS '입력작업자';

COMMENT ON COLUMN public.files.ins_date
    IS '입력일시';


-- SEQUENCE: public.seq_files

-- DROP SEQUENCE IF EXISTS public.seq_files;

CREATE SEQUENCE IF NOT EXISTS public.seq_files
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 999999999
    CACHE 1
    OWNED BY files.file_seq;

ALTER SEQUENCE public.seq_files
    OWNER TO studyproject;

COMMENT ON SEQUENCE public.seq_files
    IS '파일 시퀀스';