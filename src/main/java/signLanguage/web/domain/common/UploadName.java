package signLanguage.web.domain.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class UploadName {
    private String fileName;
    private String storeFileName;

    public UploadName(String fileName, String storeFileName) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
    }
}
