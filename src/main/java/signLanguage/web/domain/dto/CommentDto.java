package signLanguage.web.domain.dto;

import lombok.Data;
import signLanguage.web.domain.entity.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long commentId;
    private String content;
    private String userNickName;
    private LocalDateTime registryTime;

    public CommentDto(Long commentId, String content, String userNickName, LocalDateTime registryTime) {
        this.commentId = commentId;
        this.content = content;
        this.userNickName = userNickName;
        this.registryTime = registryTime;
    }

    public Long getId() {
        return commentId;
    }
}
