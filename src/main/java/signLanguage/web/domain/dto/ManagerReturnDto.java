package signLanguage.web.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.domain.common.Annotation.ClassificationValid;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.common.UploadName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ManagerReturnDto {

    private Long id;
    private Position position;
    private String introduce;
    private UploadName uploadName;

    public ManagerReturnDto(Long id, Position position, String introduce, UploadName uploadName) {
        this.id = id;
        this.position = position;
        this.introduce = introduce;
        this.uploadName = uploadName;
    }
}
