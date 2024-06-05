create table studylogs
(
    post_id     varchar(100)  not null
        constraint studylogs_pk
            primary key,
    user_id     varchar(255)  not null,
    group_id    varchar(255)  not null,
    title       varchar(100)  not null,
    content     varchar(3000) not null,
    create_date timestamp     not null,
    update_date timestamp,
    file_id     varchar(255),
    img_id      varchar(255)
);

comment on table studylogs is '스터디 결과물';

comment on column studylogs.post_id is '글ID';

comment on column studylogs.user_id is '회원ID';

comment on column studylogs.group_id is '스터디그룹ID';

comment on column studylogs.title is '제목';

comment on column studylogs.content is '내용';

comment on column studylogs.create_date is '생성일';

comment on column studylogs.update_date is '수정일';

comment on column studylogs.file_id is '파일명';

comment on column studylogs.img_id is '이미지';

