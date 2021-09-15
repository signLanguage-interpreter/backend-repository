package signLanguage.web.domain.repository.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemoryManagerMemberRepository implements ManagerMemberRepositoryInterface{

    private final EntityManager em;

    @Override
    public Long save(Interpreter interpreter) {
        em.persist(interpreter);
        return interpreter.getId();
    }

    @Override
    public Optional<Interpreter> findOne(Long id) {
        return Optional.of(em.find(Interpreter.class, id));
    }

    @Override
    public Optional<Member> findMemberWithInterpreter(Long id) {
        Member result = em.createQuery("select m from Member m " +
                "join fetch m.interpreter i", Member.class).//setParameter("memberId", id).
                getSingleResult();
        return Optional.of(result);
    }

}
