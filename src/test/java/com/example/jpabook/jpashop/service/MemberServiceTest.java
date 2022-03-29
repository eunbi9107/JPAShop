package com.example.jpabook.jpashop.service;

import com.example.jpabook.jpashop.domain.Member;
import com.example.jpabook.jpashop.Repository.MemberRepository;
import com.example.jpabook.jpashop.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//junit과 spring을 엮어서 실행할 때
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//Transactional이 testcase에 있으면 기본적으로 rollback을 해서 insert가 안보임
//rollback을 하는 이유는 반복적으로 테스트를 하기 때문에 db에 데이터가 남으면 안되기 때문
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야 한다.");
    }
}