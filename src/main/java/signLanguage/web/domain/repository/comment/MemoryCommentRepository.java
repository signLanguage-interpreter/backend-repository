package signLanguage.web.domain.repository.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.entity.Comment;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemoryCommentRepository implements CommentRepositoryInterface{
    private final EntityManager em;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public Optional<Comment> findOne(Long id) {
        return Optional.of(em.find(Comment.class, id));
    }
}
