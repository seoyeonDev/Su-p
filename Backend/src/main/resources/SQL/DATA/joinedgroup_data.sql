-- member, studygroup의 데이터가 들어가있어야 합니다.

-- 1. leader의 INSERT 구문
INSERT INTO public.joinedgroup (group_id, user_id, role, joinstatus, submission_cnt)
VALUES
('SGsylee240601101010', 'sylee', 'ROLE10', null, 0),
('SGjungsoo123240701112233', 'jungsoo123', 'ROLE10', null, 0),
('SGminji456240801205822', 'minji456', 'ROLE10', null, 0),
('SGseungwoo789240901000000', 'seungwoo789', 'ROLE10', null, 0),
('SGyeonhee101241231000000', 'yeonhee101', 'ROLE10', null, 0),
('SGjaehyun112241231111111', 'jaehyun112', 'ROLE10', null, 0),
('SGjiwon131240601000000', 'jiwon131', 'ROLE10', null, 0),
('SGhyunwoo141240701000000', 'hyunwoo141', 'ROLE10', null, 0),
('SGsuyeon155240801000000', 'suyeon155', 'ROLE10', null, 0),
('SGjungho201240901000000', 'jungho201', 'ROLE10', null, 0),
('SGhyejin223241001000000', 'hyejin223', 'ROLE10', null, 0),
('SGjaemin251241101000000', 'jaemin251', 'ROLE10', null, 0),
('SGsooyeon275240601000000', 'sooyeon275', 'ROLE10', null, 0),
('SGdonghyuk301240701000000', 'donghyuk301', 'ROLE10', null, 0);

-- 2. 일반 참여자의 INSERT 구문
INSERT INTO public.joinedgroup (group_id, user_id, role, joinstatus, submission_cnt)
VALUES
('SGsylee240601101010', 'jiwoo322', 'ROLE20', 'PERM10', 0),
('SGsylee240601101010', 'jiho334', 'ROLE20', 'PERM20', 0),
('SGsylee240601101010', 'yuna362', 'ROLE20', 'PERM20', 0),
('SGjungsoo123240701112233', 'eunji387', 'ROLE20', 'PERM10', 0),
('SGjungsoo123240701112233', 'jaehwan410', 'ROLE20', 'PERM20', 0),
('SGjungsoo123240701112233', 'soyeon435', 'ROLE20', 'PERM20', 0),
('SGminji456240801205822', 'jungmin455', 'ROLE20', 'PERM10', 0),
('SGminji456240801205822', 'jiyeon478', 'ROLE20', 'PERM20', 0),
('SGseungwoo789240901000000', 'seungmin501', 'ROLE20', 'PERM10', 0),
('SGseungwoo789240901000000', 'eunji387', 'ROLE20', 'PERM20', 0),
('SGyeonhee101241231000000', 'jaehwan410', 'ROLE20', 'PERM10', 0),
('SGyeonhee101241231000000', 'sooyeon275', 'ROLE20', 'PERM20', 0),
('SGjaehyun112241231111111', 'jungmin455', 'ROLE20', 'PERM10', 0),
('SGjaehyun112241231111111', 'jiyeon478', 'ROLE20', 'PERM20', 0),
('SGjiwon131240601000000', 'jiho334', 'ROLE20', 'PERM10', 0),
('SGjiwon131240601000000', 'yuna362', 'ROLE20', 'PERM20', 0),
('SGhyunwoo141240701000000', 'jiwoo322', 'ROLE20', 'PERM10', 0),
('SGhyunwoo141240701000000', 'jiyeon478', 'ROLE20', 'PERM20', 0),
('SGsuyeon155240801000000', 'yuna362', 'ROLE20', 'PERM10', 0),
('SGsuyeon155240801000000', 'jiwoo322', 'ROLE20', 'PERM20', 0),
('SGjungho201240901000000', 'jiho334', 'ROLE20', 'PERM10', 0),
('SGjungho201240901000000', 'yuna362', 'ROLE20', 'PERM20', 0),
('SGhyejin223241001000000', 'jiwoo322', 'ROLE20', 'PERM10', 0),
('SGhyejin223241001000000', 'jiho334', 'ROLE20', 'PERM20', 0),
('SGjaemin251241101000000', 'yuna362', 'ROLE20', 'PERM10', 0),
('SGjaemin251241101000000', 'jiyeon478', 'ROLE20', 'PERM20', 0),
('SGsooyeon275240601000000', 'jiyeon478', 'ROLE20', 'PERM10', 0),
('SGsooyeon275240601000000', 'jiwoo322', 'ROLE20', 'PERM20', 0),
('SGdonghyuk301240701000000', 'jiho334', 'ROLE20', 'PERM10', 0),
('SGdonghyuk301240701000000', 'yuna362', 'ROLE20', 'PERM20', 0);


-- 테스트를 위한 새로운 joiendgroup DATA 추가
-- 종료된 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010001', 'yujin2025', 'ROLE10', 'PERM20', 35);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010001', 'minji2025', 'ROLE20', 'PERM20', 35);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010001', 'hyein2025', 'ROLE20', 'PERM20', 38);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010001', 'danielle2025', 'ROLE20', 'PERM40', 10); -- 강퇴

-- 운영중인 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'yujin2025', 'ROLE10', 'PERM20', 2);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'minji2025', 'ROLE20', 'PERM20', 2);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'rei2025', 'ROLE20', 'PERM20', 2);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'gaeul2025', 'ROLE20', 'PERM20', 2);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'yujin2025', 'ROLE20', 'PERM20', 1);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'hyein2025', 'ROLE20', 'PERM20', 1);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'haerin2025', 'ROLE20', 'PERM30', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010002', 'danielle2025', 'ROLE20', 'PERM40', 0); -- 강퇴

-- 모집중인 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'yujin2025', 'ROLE10', 'PERM20', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'minji2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'leeseo2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'liz2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'wonyoung2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'gaeul2025', 'ROLE20', 'PERM20', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'hyein2025', 'ROLE20', 'PERM20', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'haerin2025', 'ROLE20', 'PERM30', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010004', 'danielle2025', 'ROLE20', 'PERM30', 0);


-- 종료된 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010005', 'minji2025', 'ROLE10', 'PERM20', 21);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010005', 'yujin2025', 'ROLE20', 'PERM20', 21);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010005', 'rei2025', 'ROLE20', 'PERM20', 20);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010005', 'gaeul2025', 'ROLE20', 'PERM20', 18);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010005', 'hyein2025', 'ROLE20', 'PERM40', 10); -- 강퇴

-- 운영중인 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010006', 'minji2025', 'ROLE10', 'PERM20', 5);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010006', 'yujin2025', 'ROLE20', 'PERM20', 4);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010006', 'leeseo2025', 'ROLE20', 'PERM20', 5);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010006', 'liz2025', 'ROLE20', 'PERM20', 4);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010006', 'wonyoung2025', 'ROLE20', 'PERM40', 2); -- 강퇴

-- 모집중인 스터디
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010008', 'minji2025', 'ROLE10', 'PERM20', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010008', 'yujin2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010008', 'hyein2025', 'ROLE20', 'PERM10', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010008', 'haerin2025', 'ROLE20', 'PERM20', 0);
INSERT INTO public.joinedgroup(group_id, user_id, role, joinstatus, submission_cnt)
VALUES ('2401010008', 'danielle2025', 'ROLE20', 'PERM30', 0);
