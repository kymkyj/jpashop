package com.kym.boot.jpashop.repository;

import com.kym.boot.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
    /*
        원래는 엔티티 매니저를 사용하려면 @PersistenceContext 를 사용해야 하는데
        스프링 부트에서 Data.jpa 를 의존성 주입하면 Autowired를 쓰기만해도 사용할 수 있도록 처리해준다.
     */
    @Autowired
    private final EntityManager em;

//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
