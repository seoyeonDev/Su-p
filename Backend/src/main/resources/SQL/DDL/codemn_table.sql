create table "codeMn"
(
    cd_type   varchar(20)  not null,
    comm_cd   varchar(100) not null,
    comm_cdnm varchar(100),
    constraint "codeMn_pk"
        primary key (cd_type, comm_cd)
);

comment on table "codeMn" is '코드관리';

comment on column "codeMn".cd_type is '코드 분류';

comment on column "codeMn".comm_cd is '코드';

comment on column "codeMn".comm_cdnm is '코드명';
