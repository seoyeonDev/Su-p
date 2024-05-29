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
