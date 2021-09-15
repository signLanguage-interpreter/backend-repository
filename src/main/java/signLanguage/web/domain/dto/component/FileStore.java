package signLanguage.web.domain.dto.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.domain.dto.UploadImage;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    private HttpServletRequest request;
    private String fileDir = request.getServletContext().getContextPath();

    @Autowired
    public FileStore(HttpServletRequest request) {
        this.request = request;
    }


    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public UploadImage storeImage(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();

        int pos = originalFilename.lastIndexOf(".");
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + originalFilename.substring(pos + 1);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadImage(originalFilename, storeFileName);

    }
}
