package signLanguage.web.domain.repository.member;

import signLanguage.web.domain.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryInterface {
    public Long save(Member member);
    public Optional<Member> findOne(Long id);
    public Optional<Member> findByName(String name);
}
