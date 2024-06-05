-- Table: public.joinedgroup

-- DROP TABLE IF EXISTS public.joinedgroup;

CREATE TABLE IF NOT EXISTS public.joinedgroup
(
    group_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role character varying(10) COLLATE pg_catalog."default" NOT NULL DEFAULT '200'::character varying,
    joinstatus character varying(10) COLLATE pg_catalog."default",
    submission_cnt integer
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.joinedgroup
    OWNER to postgres;

COMMENT ON TABLE public.joinedgroup
    IS '가입한 그룹';

COMMENT ON COLUMN public.joinedgroup.group_id
    IS '스터디그룹 ID';

COMMENT ON COLUMN public.joinedgroup.user_id
    IS '회원 ID';

COMMENT ON COLUMN public.joinedgroup.role
    IS '권한';

COMMENT ON COLUMN public.joinedgroup.joinstatus
    IS '신청 상태';

COMMENT ON COLUMN public.joinedgroup.submission_cnt
    IS '제출 횟수';