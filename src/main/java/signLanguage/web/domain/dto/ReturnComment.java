package signLanguage.web.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReturnComment {
    @NotBlank(message = "댓글은 빈칸을 유지하지 못합니다.")
    private String content;
}
