package com.kym.boot.jpashop.service;

import com.kym.boot.jpashop.domain.Member;
import com.kym.boot.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// JPA관련해서는 트랜잭션 안에서 모두 이루어져야한다
// 스프링이 제공하는 트랜잭션 어노테이션이랑 javax가 제공하는게 있는데 스프링이 제공해주는게 더 낫다.
@Transactional(readOnly = true)
//@AllArgsConstructor
@RequiredArgsConstructor // final을 가지면서 생성자를 만들어준다.
public class MemberService {

    // 변경할 일이 없기떄문에 final로 적어주는 것이 추세
    private final MemberRepository memberRepository;

    /*
        요즘에 뜨는 추세는 생성자 injection
        스프링이 생성자가 한개만 있을 경우에는 어노테이션 안써줘도 잡아준다
    */
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /*
        setter Repository를 적어서 injection 하는 방식
        테스트 코드 작성할 떄 set메서드에 값을 넣어볼 수 있다.
     */
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }


    /**
     * 회원 가입
     */
    @Transactional // 여기에있는 안쪽에 있는 트랜잭션이 우선권을 갖는다.
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    // db한테 이건 읽기 전용이라는거를 알려주기위해 readOnly를 적으면 좋다
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
