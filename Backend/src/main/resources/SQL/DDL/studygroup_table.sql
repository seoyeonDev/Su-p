-- Table: public.studygroup

-- DROP TABLE IF EXISTS public.studygroup;

CREATE TABLE IF NOT EXISTS public.studygroup
(
    group_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    leader_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    title character varying(1000) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    study_desc character varying(4000) COLLATE pg_catalog."default" NOT NULL,
    kind character varying(100) COLLATE pg_catalog."default",
    status character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT false,
    mem_cnt integer NOT NULL DEFAULT 0,
    startdate timestamp without time zone NOT NULL,
    enddate timestamp without time zone NOT NULL,
    view_cnt integer NOT NULL DEFAULT 0,
    chk_m character varying(10) COLLATE pg_catalog."default" NOT NULL,
    chk_min_cnt integer NOT NULL,
    chk_total_cnt integer,
    penalty integer NOT NULL,
    CONSTRAINT studygroup_pkey PRIMARY KEY (group_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.studygroup
    OWNER to postgres;

COMMENT ON TABLE public.studygroup
    IS '스터디 그룹 정보';

COMMENT ON COLUMN public.studygroup.group_id
    IS '그룹 ID';

COMMENT ON COLUMN public.studygroup.leader_id
    IS '그룹 리더의 ID';

COMMENT ON COLUMN public.studygroup.title
    IS '스터디 그룹의 모집 제목';

COMMENT ON COLUMN public.studygroup.name
    IS '스터디 그룹 이름';

COMMENT ON COLUMN public.studygroup.study_desc
    IS '스터디 그룹에 대한 설명';

COMMENT ON COLUMN public.studygroup.kind
    IS '스터디 그룹의 종류';

COMMENT ON COLUMN public.studygroup.status
    IS '스터디 그룹의 상태(모집중 진행중 종료)';

COMMENT ON COLUMN public.studygroup.mem_cnt
    IS '스터디 그룹의 최대 멤버 수';

COMMENT ON COLUMN public.studygroup.startdate
    IS '스터디 그룹의 시작 날짜';

COMMENT ON COLUMN public.studygroup.enddate
    IS '스터디 그룹의 종료 날짜';

COMMENT ON COLUMN public.studygroup.view_cnt
    IS '스터디 그룹의 조회수';

COMMENT ON COLUMN public.studygroup.chk_m
    IS '월 단위 또는 주 단위의 체크 횟수';

COMMENT ON COLUMN public.studygroup.chk_min_cnt
    IS '최소 제출 횟수';

COMMENT ON COLUMN public.studygroup.chk_total_cnt
    IS '전체 제출 횟수';

COMMENT ON COLUMN public.studygroup.penalty
    IS '패널티의 개수';




-- 이미 테이블이 있다면
-- 0. status 설정 변경
ALTER TABLE public.studygroup
ALTER COLUMN status TYPE varchar(10);

-- 1. chk_d와 chk_cnt 컬럼 삭제
ALTER TABLE public.studygroup
DROP COLUMN IF EXISTS chk_d,
DROP COLUMN IF EXISTS chk_cnt;


-- 2. chk_min_cnt, chk_total_cnt, penalty 컬럼 추가
ALTER TABLE public.studygroup
ADD COLUMN chk_min_cnt integer,
ADD COLUMN chk_total_cnt integer,
ADD COLUMN penalty integer;


-- 3. 기존 데이터에서 chk_min_cnt와 penalty의 NULL 값을 기본값으로 업데이트
UPDATE public.studygroup
SET chk_min_cnt = 0
WHERE chk_min_cnt IS NULL;

UPDATE public.studygroup
SET penalty = 0
WHERE penalty IS NULL;


-- 4. chk_min_cnt와 penalty 컬럼에 NOT NULL 제약 조건 추가
ALTER TABLE public.studygroup
ALTER COLUMN chk_min_cnt SET NOT NULL,
ALTER COLUMN penalty SET NOT NULL;


-- 5. 위에 있는 comment 추가하기