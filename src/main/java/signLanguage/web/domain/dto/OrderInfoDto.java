package signLanguage.web.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import signLanguage.web.domain.common.Annotation.ClassificationValid;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.OrderStatus;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString(of = {"subject"})
@EqualsAndHashCode(of = "receptionDate")
public class OrderInfoDto {

    private OrderStatus orderStatus;

    @NotBlank
    @NotNull(message = "입력된 제목이 없어요.")
    @Size(max = 30, message = "제목은 30자 이내여야 해요.")
    private String subject;

    @NotBlank
    @NotNull(message = "입력된 내용이 없어요.")
    @Size(max = 500, message = "내용은 500자 이내여야 해요.")
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    @Future(message = "날짜는 현재보다 미래여야해요.")
    private LocalDateTime receptionDate;

    @NotNull(message = "입력된 분류가 없어요.")
    @ClassificationValid(enumClass = Classification.class, message = "유효하지 않습니다.")
    private Classification classification;

    public OrderInfoDto(OrderStatus orderStatus, @NotBlank @NotNull(message = "입력된 제목이 없어요.") @Size(max = 30, message = "제목은 30자 이내여야 해요.") String subject, @NotBlank @NotNull(message = "입력된 내용이 없어요.") @Size(max = 500, message = "내용은 500자 이내여야 해요.") String content, @NotNull @Future(message = "날짜는 현재보다 미래여야해요.") LocalDateTime receptionDate, @NotNull(message = "입력된 분류가 없어요.") Classification classification) {
        this.orderStatus = orderStatus;
        this.subject = subject;
        this.content = content;
        this.receptionDate = receptionDate;
        this.classification = classification;
    }

    private List<CommentDto> commentList;
//    @NotNull(message = "통역사를 선택하지 않았어요.")
//    @Range(min = 1, max = 10, message = "통역사의 유효하지 않은 번호입니다.")
//    private Long interpreterId;


}
