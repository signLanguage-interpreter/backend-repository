package signLanguage.web.domain.repository.member;


/* Principal Details 전용 */

import org.springframework.data.jpa.repository.JpaRepository;
import signLanguage.web.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
