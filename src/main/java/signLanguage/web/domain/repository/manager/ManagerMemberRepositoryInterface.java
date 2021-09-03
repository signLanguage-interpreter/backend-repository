package signLanguage.web.domain.repository.manager;

import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ManagerMemberRepositoryInterface {
    public Long save(Interpreter interpreter);
    public Optional<Interpreter> findOne(Long id);
    public List<Interpreter> findInterpreterWithMember(Long id);
}
