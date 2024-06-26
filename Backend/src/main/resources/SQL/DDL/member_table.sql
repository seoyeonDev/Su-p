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

