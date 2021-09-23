package signLanguage.web.domain.dto;

import lombok.Data;
import signLanguage.web.domain.entity.Comment;

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

    @Data
    public static class ReturnComment{
        @NotNull(message = "댓글은 빈칸을 유지하지 못합니다.")
        private String content;
    }
}
