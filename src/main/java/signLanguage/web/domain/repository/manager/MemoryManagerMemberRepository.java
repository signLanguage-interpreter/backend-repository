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
    public List<Interpreter> findInterpreterWithMember(Long id) {
        List<Interpreter> result = em.createQuery("select i from Interpreter i " +
                "join fetch i.member", Interpreter.class).//setParameter("memberId", id).
                getResultList();
        return result;
    }
}
