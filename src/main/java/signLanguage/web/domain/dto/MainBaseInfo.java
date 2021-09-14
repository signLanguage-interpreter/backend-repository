package signLanguage.web.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.OrderStatus;

import java.time.LocalDateTime;

@Data
public class MainBaseInfo {
    private Long id;
    @JsonIgnore
    private String userNickName;
    @JsonIgnore
    private String cellPhone;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String eMail;

    private String receptionId;
    private LocalDateTime receptionDate;
    private OrderStatus status;
    private String subject;

    public MainBaseInfo(Long id,
                        String receptionId,
                        LocalDateTime receptionDate,
                        String subject
                        ,OrderStatus status) {
        this.id = id;
        this.receptionId = receptionId;
        this.receptionDate = receptionDate;
        this.status = status;
        this.subject = subject;
    }

    private Classification classification;

    public MainBaseInfo(Long id,
                        String userNickName,
                        String cellPhone,
                        String username,
                        String eMail,
                        String receptionId,
                        OrderStatus status,
                        Classification classification,
                        String subject,
                        LocalDateTime receptionDate) {
        this.id = id;
        this.userNickName = userNickName;
        this.cellPhone = cellPhone;
        this.username = username;
        this.eMail = eMail;
        this.receptionId = receptionId;
        this.receptionDate = receptionDate;
        this.status = status;
        this.classification = classification;
        this.subject = subject;
    }
}
