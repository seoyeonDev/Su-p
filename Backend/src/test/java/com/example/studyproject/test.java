package com.example.studyproject;

import com.example.studyproject.mapper.memberMapper;
import com.example.studyproject.member.Member;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSession;

public class test {
    public static void main(String[] args) {

        class MemberRepository {
            public Member getMember(int id) {
                SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
                try {
                    Member user = session.selectOne("com.example.studyproject.mapper.memberMapper.selectMember", id);
                    System.out.println(user);
                    return user;
                } finally {
                    session.close();
                }
            }
        }


//        @Resource(name = "sqlSession")
//        private SqlSession session;
//        session.insert("")
////            memberMapper mapper = session.getMapper(memberMapper.class);
////            Member user = mapper.selectMember(1);
//            System.out.println(mapper.selectMember());

    }
}
