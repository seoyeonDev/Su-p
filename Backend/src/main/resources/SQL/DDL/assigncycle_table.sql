-- Table: public.sg_assgincycle

-- DROP TABLE IF EXISTS public.sg_assigncycle;

CREATE TABLE IF NOT EXISTS public.sg_assigncycle
(
    startdate timestamp without time zone NOT NULL,
    enddate timestamp without time zone NOT NULL,
    group_id character(255) COLLATE pg_catalog."default" NOT NULL,
    assgincycle integer NOT NULL,
    CONSTRAINT sg_assigncycle_pkey PRIMARY KEY (startdate, enddate)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sg_assigncycle
    OWNER to studyproject;

COMMENT ON TABLE public.sg_assigncycle
    IS '스터디그룹 회차';