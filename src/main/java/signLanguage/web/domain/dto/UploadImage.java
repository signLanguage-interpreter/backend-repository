package signLanguage.web.domain.dto;

import lombok.Data;

@Data
public class UploadImage {
    private String fileName;
    private String storeFileName;

    public UploadImage(String fileName, String storeFileName) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
    }
}
