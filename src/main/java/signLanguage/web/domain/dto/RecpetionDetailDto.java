package signLanguage.web.domain.dto;

import lombok.Data;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecpetionDetailDto {
    private String subject;
    private String content;
    private LocalDateTime receptionDate;
    private Classification classification;
    private OrderStatus status;
    private Long contentId;
    String commentContent;
    String userNickName;
    LocalDateTime registryTime;

    public RecpetionDetailDto(String subject,
                              String content,
                              LocalDateTime receptionDate,
                              Classification classification,
                              OrderStatus status,
                              Long contentId,
                              String commentContent,
                              String userNickName,
                              LocalDateTime registryTime)
    {
        this.subject = subject;
        this.content = content;
        this.receptionDate = receptionDate;
        this.classification = classification;
        this.status = status;
        this.contentId = contentId;
//        this.commentList = commentList;
        this.commentContent = commentContent;
        this.userNickName = userNickName;
        this.registryTime = registryTime;
    }
}
