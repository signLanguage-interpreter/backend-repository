package signLanguage.web.domain.repository.comment;

import signLanguage.web.domain.entity.Comment;
import signLanguage.web.domain.entity.ReceptionOrder;

import java.util.Optional;

public interface CommentRepositoryInterface {
    public Long save(Comment comment);
    public Optional<Comment> findOne(Long id);
}
