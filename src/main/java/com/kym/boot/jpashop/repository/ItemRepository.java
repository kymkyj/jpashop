package com.kym.boot.jpashop.repository;

import com.kym.boot.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        // 상품은 JPA에 저장하기 전까지는 아이디 값이 없다.
        // 아이디값이 없다는 거는 완전히 새로 생성한 객체라는 뜻
        if(item.getId() == null){
            // persist해서 새로 등록하는 것
            em.persist(item);
        }else{
            // update와 비슷한 것
            em.merge(item);
        }
    }

    // 단건 조회
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    // 다중건 조회
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
