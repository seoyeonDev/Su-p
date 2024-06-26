-- Table: public.sg_assigncycle

-- DROP TABLE IF EXISTS public.sg_assigncycle;

CREATE TABLE IF NOT EXISTS public.sg_assigncycle
(
    group_id character(255) COLLATE pg_catalog."default" NOT NULL,
    startdate character(255) NOT NULL,
    enddate character(255) NOT NULL,
    assigncycle character(255) NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sg_assigncycle
    OWNER to studyproject;

COMMENT ON TABLE public.sg_assigncycle
    IS '스터디그룹 회차';