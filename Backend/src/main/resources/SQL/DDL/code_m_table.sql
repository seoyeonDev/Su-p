-- auto-generated definition
create table code_m
(
    cd_type    varchar(20) not null
        constraint code_m_pk
            primary key,
    cd_type_nm varchar(100)
);

comment on table code_m is '코드관리';

comment on column code_m.cd_type is '코드 분류';

comment on column code_m.cd_type_nm is '코드 분류명';


