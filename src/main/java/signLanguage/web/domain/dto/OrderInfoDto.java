package signLanguage.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class OrderInfoDto {

    @NotNull(message = "입력된 제목이 없어요.")
    @Size(max = 30, message = "제목은 30자 이내여야 해요.")
    private String subject;

    @NotNull(message = "입력된 내용이 없어요.")
    @Size(max = 500, message = "내용은 500자 이내여야 해요.")
    private String content;
}
