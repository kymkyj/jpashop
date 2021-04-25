package com.kym.boot.jpashop;

import com.kym.boot.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// junit한테 알려줘야한다.
// 난 Spring과 관련한거로 테스트할꺼다!
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;


    @Test
    // spring 에서 제공하는 트랜잭션을 사용하는게 좋다 부가옵션으로 쓸수있는것이 많음
    // 트랜잭션이 테스트쪽에 있으면 끝나고나서 롤백해버린다.
    @Transactional
    @Rollback(false) // data가 직접 들어가는걸 확인하려면 Rollback을 써주면된다.
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member: " + (findMember == member));

    }
}
