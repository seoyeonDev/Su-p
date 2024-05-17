-- auto-generated definition
create table code_d
(
    cd_type   varchar(20)  not null
        constraint cd_type
            references code_m,
    comm_cd   varchar(100) not null,
    comm_cdnm varchar(100),
    constraint code_d_pk
        primary key (cd_type, comm_cd)
);

comment on table code_d is '상세 코드';

comment on column code_d.cd_type is '코드 분류';

comment on column code_d.comm_cd is '상세코드';

comment on column code_d.comm_cdnm is '상세코드명';

