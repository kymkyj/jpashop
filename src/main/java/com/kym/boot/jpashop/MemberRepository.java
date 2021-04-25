package com.kym.boot.jpashop;

import com.kym.boot.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository // 이 어노테이션안에 @Component으로 인해 스프링 빈에 자동 등록된다.
public class MemberRepository {

    // JPA에서 사용하는 엔티티 매니저를 스프링 부트가 자동으로 주입해준다
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
