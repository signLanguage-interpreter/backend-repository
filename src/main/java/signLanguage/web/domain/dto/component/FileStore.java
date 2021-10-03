package signLanguage.web.domain.dto.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.domain.dto.UploadImage;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class FileStore {

    public String getFullPath(String fileName,HttpServletRequest request){
        return request.getServletContext().getRealPath("/") + fileName;
    }

    public UploadImage storeImage(MultipartFile multipartFile, HttpServletRequest request) throws IOException {

        if(multipartFile == null || multipartFile.isEmpty()){
            return new UploadImage(null, null);
        }

        String originalFilename = multipartFile.getOriginalFilename();

        int pos = originalFilename.lastIndexOf(".");
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + "." + originalFilename.substring(pos + 1);

        //여기서 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName, request)));
        //여기서 저장

        return new UploadImage(originalFilename, storeFileName);

    }
}
