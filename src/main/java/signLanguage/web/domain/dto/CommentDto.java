package signLanguage.web.domain.dto;

import lombok.Data;
import signLanguage.web.domain.entity.Comment;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String userNickName;
    private LocalDateTime registryTime;

    public CommentDto(Long id, String content,String userNickName, LocalDateTime registryTime) {
        this.id = id;
        this.content = content;
        this.userNickName = userNickName;
        this.registryTime = registryTime;
    }
}
