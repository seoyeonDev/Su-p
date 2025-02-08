
-- 사용자 계정 권한
INSERT INTO public.code_m (cd_type, cd_type_nm) VALUES ('AUTH', '사용자 계정 권한');
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('AUTH', 'AUTH10', '시스템 관리자');
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('AUTH', 'AUTH20', '일반 사용자');

-- 스터디그룹 제출기준
INSERT INTO public.code_m (cd_type, cd_type_nm) VALUES ('SUBM'::varchar(20), '스터디그룹 제출기준'::varchar(100));
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('SUBM'::varchar(20), 'SUBM10'::varchar(100), '7일(1주)'::varchar(100));
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('SUBM'::varchar(20), 'SUBM20'::varchar(100), '30일(1달)'::varchar(100));


-- 스터디 활성화 상태
INSERT INTO public.code_m (cd_type, cd_type_nm) VALUES ('STAT'::varchar(20), '스터디 활성화 상태'::varchar(100))
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('STAT'::varchar(20), 'STAT00'::varchar(100), '모집중(신청자 0명)'::varchar(100))
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('STAT'::varchar(20), 'STAT10'::varchar(100), '모집중'::varchar(100))
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('STAT'::varchar(20), 'STAT20'::varchar(100), '진행중'::varchar(100))
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('STAT'::varchar(20), 'STAT30'::varchar(100), '종료'::varchar(100))

