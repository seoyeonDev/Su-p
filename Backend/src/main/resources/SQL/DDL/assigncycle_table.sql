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

-- pk 추가
alter table public.sg_assigncycle
    add constraint sg_assigncycle_pk
        primary key (group_id, assigncycle);

-- group_id char > varchar 로 타입 변경
alter table public.sg_assigncycle
    alter column group_id type varchar(255) using group_id::varchar(255);

-- 컬럼 타입 변경
alter table public.sg_assigncycle
    alter column startdate type date using startdate::date;

alter table public.sg_assigncycle
    alter column enddate type date using enddate::date;

alter table public.sg_assigncycle
    alter column assigncycle type int using assigncycle::int;