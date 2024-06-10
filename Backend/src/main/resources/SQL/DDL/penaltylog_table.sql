-- Table: public.penaltylog

-- DROP TABLE IF EXISTS public.penaltylog;

CREATE TABLE IF NOT EXISTS public.penaltylog
(
    user_id character(255) COLLATE pg_catalog."default" NOT NULL,
    group_id character(255) COLLATE pg_catalog."default" NOT NULL,
    title character(500) COLLATE pg_catalog."default" NOT NULL,
    logcontent character(1000) COLLATE pg_catalog."default" NOT NULL,
    penaltydate timestamp without time zone[] NOT NULL
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.penaltylog
    OWNER to studyproject;

COMMENT ON TABLE public.penaltylog
    IS '패널티 로그';

COMMENT ON COLUMN public.penaltylog.user_id
    IS '유저 ID';

COMMENT ON COLUMN public.penaltylog.group_id
    IS '그룹 ID';

COMMENT ON COLUMN public.penaltylog.title
    IS '그룹 제목';

COMMENT ON COLUMN public.penaltylog.logcontent
    IS '패널티 내용';

COMMENT ON COLUMN public.penaltylog.penaltydate
    IS '패널티 날짜';