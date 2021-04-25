package com.kym.boot.jpashop.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue // 시퀀스 자동생성 되게 만들어준다.
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 난 order테이블의 member의 거울이다.
    // 읽기만 하겠다! 라는 뜻
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
