-- Table: public.sg_assgincycle

-- DROP TABLE IF EXISTS public.sg_assgincycle;

CREATE TABLE IF NOT EXISTS public.sg_assgincycle
(
    startdate timestamp without time zone NOT NULL,
    enddate timestamp without time zone NOT NULL,
    group_id character(255) COLLATE pg_catalog."default" NOT NULL,
    assgincycle integer NOT NULL,
    CONSTRAINT sg_assgincycle_pkey PRIMARY KEY (startdate, enddate)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sg_assgincycle
    OWNER to studyproject;

COMMENT ON TABLE public.sg_assgincycle
    IS '스터디그룹 회차';