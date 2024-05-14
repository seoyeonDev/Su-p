CREATE TABLE joinedgroup
(
	group_id	VARCHAR(255)	NOT NULL,
	user_id	VARCHAR(255)	NOT NULL,
	role	VARCHAR(10)	NOT NULL DEFAULT '200',
	joinstatus	VARCHAR(10)	NULL,
	submission_cnt	INT	NULL
);

comment on table joinedgroup is '가입한 그룹';

comment on column joinedgroup.group_id is '스터디그룹 ID';

comment on column joinedgroup.user_id is '회원 ID';

comment on column joinedgroup.role is '권한';

comment on column joinedgroup.joinstatus is '신청 상태';

comment on column joinedgroup.submission_cnt is '제출 횟수';

alter table member
    owner to postgres;
