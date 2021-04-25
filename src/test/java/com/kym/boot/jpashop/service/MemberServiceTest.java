package com.kym.boot.jpashop.service;

import com.kym.boot.jpashop.domain.Member;
import com.kym.boot.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 실행할때 스프링이랑 같이 시작할래? 묻는거
@SpringBootTest // 스프링부트를 띠운상태로 테스트할 떄
// spring 에서 제공하는 트랜잭션을 사용하는게 좋다 부가옵션으로 쓸수있는것이 많음
// 트랜잭션이 테스트쪽에 있으면 끝나고나서 롤백해버린다.
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // rollback 이지만 db에 쿼리날리는걸 보고싶다 하면!
    @Autowired EntityManager em;

    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception {
        //given 이런게 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when 이렇게 하면
        Long saveId = memberService.join(member);

        //then 이렇게 된다.
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
//        try{
            memberService.join(member2); // 예외가 발생해야한다. 이름이 kim으로 같으니까
//        }catch (IllegalStateException e){
//            return;
//        }
        //then
        fail("예외가 발생해야 한다.");
    }

}