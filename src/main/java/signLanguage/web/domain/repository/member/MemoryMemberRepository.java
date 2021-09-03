package signLanguage.web.domain.repository.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemoryMemberRepository implements MemberRepositoryInterface {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Optional<Member> findOne(Long id){
        return Optional.of(em.find(Member.class, id));
    }

    public Optional<Member> findByName(String name){
        List<Member> findMembers = em.createQuery("select m from Member m where m.username = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return findMembers.stream().filter(m -> m.getUsername().equals(name)).findFirst();
    }




}
