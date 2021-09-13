package signLanguage.web.domain.dto;

import lombok.Data;
import signLanguage.web.domain.common.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderManagerDto {
    private String receptionId;
    private String subject;
    private LocalDateTime localDateTime;
    private OrderStatus status;
    private Long orderCount;

    public OrderManagerDto(String receptionId, String subject, LocalDateTime localDateTime, OrderStatus status) {
        this.receptionId = receptionId;
        this.subject = subject;
        this.localDateTime = localDateTime;
        this.status = status;
    }
}
