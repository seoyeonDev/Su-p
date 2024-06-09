
-- 사용자 계정 권한
INSERT INTO public.code_m (cd_type, cd_type_nm) VALUES ('AUTH', '사용자 계정 권한');
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('AUTH', 'AUTH10', '시스템 관리자');
INSERT INTO public.code_d (cd_type, comm_cd, comm_cdnm) VALUES ('AUTH', 'AUTH20', '일반 사용자');