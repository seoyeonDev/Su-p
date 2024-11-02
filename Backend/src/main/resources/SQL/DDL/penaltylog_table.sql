-- Table: public.penaltylog

DROP TABLE IF EXISTS public.penaltylog;

CREATE TABLE IF NOT EXISTS public.penaltylog
(
    user_id character(255) COLLATE pg_catalog."default" NOT NULL,
    group_id character(255) COLLATE pg_catalog."default" NOT NULL,
    logcontent character(1000) COLLATE pg_catalog."default" NOT NULL,
    penalty_round integer NOT NULL
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

COMMENT ON COLUMN public.penaltylog.logcontent
    IS '패널티 내용';

COMMENT ON COLUMN public.penaltylog.penalty_round
    IS '회차(몇번째인지)';

-- 241102 이서연, sg_assigncycle 의 assigncycle 컬럼과 일치하도록 변경
alter table public.penaltylog
    alter column penalty_round type char(255) using penalty_round::char(255);

