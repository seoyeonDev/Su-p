create table member
(
    user_id     varchar(255)            not null
        primary key,
    password    varchar(255)            not null,
    name        varchar(100)            not null,
    nickname    varchar(100)            not null,
    email       varchar(100)            not null,
    joindate    timestamp default now() not null,
    profile_img varchar(1000),
    fail_num    integer   default 0,
    lock_yn     char      default 'N'::bpchar
);

comment on table member is '회원정보 ';

comment on column member.user_id is '회원 ID';

comment on column member.password is '비밀번호';

comment on column member.name is '이름';

comment on column member.nickname is '닉네임';

comment on column member.email is '이메일';

comment on column member.joindate is '가입일';

comment on column member.profile_img is '프로필사진';

comment on column member.fail_num is '비밀번호 오류 횟수';

comment on column member.lock_yn is '계정 잠금여부';
--
-- alter table member
--     owner to postgres;

-- 사용자 계정 권한 추가

alter table public.member
    add "authorization" varchar(100);

comment on column public.member."authorization" is '사용자 계정 권한';


-- 테스트를 위한 새로운 member DATA 추가
INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('minji2025', 'cbb059a43536a0e7511425fdc55bc1392fbca87aa0767be5eafdbcedfaf58a53', '김민지', '곰아지', 'minji2025@naver.com', '2023-12-08 13:42:28', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('leeseo2025', 'ff54d8a132bbf05393a40920c3bff6e5e8c7321f58a1efbc982d96fd14e10fa4', '이현서', '아기호랑이', 'leeseo2025@naver.com', '2024-01-10 8:06:28', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('liz2025', '2fcb70a7f812a04d84b8213fcc9477e04da8cbbc2686a8f50754864d1dea1f7a', '김지원', '리즈', 'liz2025@naver.com', '2023-07-15 13:05:39', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('wonyoung2025',  '9526d3e92ad1be2085c98a5b94c39740e0081a3258f9977b5d12842bb84f2bc4', '장원영', '럭키비키', 'wonyoung2025@naver.com', '2023-11-21 19:05:04', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('rei2025', 'b2981767e2b1d77ca76c6952af2879f83a976960888266f9c9cbaf412ceac467', '레이', '콩순이', 'rei2025@naver.com', '2024-01-02 14:04:19', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('gaeul2025', 'e8f31a6efe7b35ec6a013ebdd4dc585bd3bbf32dbf4b335b1fb29b3701d74f9c', '가을', '가을선배', 'gaeul2025@naver.com', '2023-06-04 13:03:46', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('yujin2025', 'c4a1837ef9fe5998a0e8ccfe4b417f9f487c0980cf06f2ffd8df2c2c0e008101', '안유진', '안댕댕', 'yujin2025@naver.com', '2023-07-20 17:02:25', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('hyein2025', 'adde6abcee39f10702899bfafbd60901540a6a9c5741d986239f3cbe5109c742', '이혜인', '혠이', 'hyein2025@naver.com', '2023-10-10 15:01:29', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('haerin2025', '27e350f084a3145b20cf4e00bce2e7a710ac9eb8f3db8a4423b1475404f89b7a', '강해린', '고양이', 'haerin2025@naver.com', '2024-01-27 13:00:37', NULL, 0, 'N', 'AUTH20');

INSERT INTO public.member (user_id, password, name, nickname, email, joindate, profile_img, fail_num, lock_yn, "authorization")
VALUES ('danielle2025', 'e15b907719021362283c83702e4e90857c7f8742fc7240259f289fb07ea3d7a8', '다니엘', '다니', 'denielle2025@naver.com', '2024-02-10 12:58:57', NULL, 0, 'N', 'AUTH20');