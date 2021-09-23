package signLanguage.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.domain.common.Annotation.ClassificationValid;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.Position;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ManagerDto {

    @NotNull(message = "입력된 직책이 없어요.")
//    @ClassificationValid(enumClass = Classification.class, message = "유효하지 않습니다.")
    private String position;

    @NotNull(message = "입력된 직책이 없어요.")
    @Size(max = 100, message = "100자 이내로 입력해야해요.")
    private String introduce;
    private MultipartFile imageFile;


}
