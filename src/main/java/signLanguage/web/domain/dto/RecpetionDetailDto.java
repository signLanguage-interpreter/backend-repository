package signLanguage.web.domain.dto;


import lombok.Data;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.OrderStatus;

import java.time.LocalDateTime;


@Data
public class RecpetionDetailDto {
    private String subject;
    private String content;
    private LocalDateTime receptionDate;
    private Classification classification;
    private OrderStatus status;

    private Long commentId;
    private String commentContent;
    private String userNickName;
    private LocalDateTime registryTime;

    public RecpetionDetailDto(String subject,
                              String content,
                              LocalDateTime receptionDate,
                              Classification classification,
                              OrderStatus status,
                              Long commentId,
                              String commentContent,
                              String userNickName,
                              LocalDateTime registryTime) {
        this.subject = subject;
        this.content = content;
        this.receptionDate = receptionDate;
        this.classification = classification;
        this.status = status;
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.userNickName = userNickName;
        this.registryTime = registryTime;

    }
}
