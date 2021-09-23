package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.dto.ManagerReturnDto;
import signLanguage.web.domain.dto.UploadImage;
import signLanguage.web.domain.dto.component.FileStore;
import signLanguage.web.servie.ManagerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final FileStore fileStore;

    @ResponseBody
    @GetMapping("/managerInfo")
    public ManagerReturnDto getManagerInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        ManagerReturnDto managerReturnDto = managerService.printInterpreter(principalDetails.getMember().getId());
        System.out.println("managerReturnDto.toString() = " + managerReturnDto.toString());
        return managerService.printInterpreter(principalDetails.getMember().getId());
    }

    @ResponseBody
    @PostMapping(value = "/managerInfo")
    public void postManagerInfo(@Valid ManagerDto managerDto, BindingResult bindingResult,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                HttpServletRequest request) throws IOException {

        log.info("{} ==========================================={}=========================================== {}", managerDto.getImageFile(), managerDto.getIntroduce(), managerDto.getPosition());
        MultipartFile imageFile = managerDto.getImageFile();

        UploadImage uploadImage = fileStore.storeImage(imageFile,request);

        System.out.println(managerDto.getPosition().substring(13, managerDto.getPosition().length() - 2));

        Position byPosition = Position.findByPosition(managerDto.getPosition().substring(13, managerDto.getPosition().length() - 2));
        System.out.println(managerDto.getPosition().substring(13, managerDto.getPosition().length() - 2));
        managerService.addInterpreter(managerDto.getIntroduce().substring(14,managerDto.getIntroduce().length()-2),
                byPosition,
                uploadImage,
                principalDetails.getMember().getId());

    }
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename,HttpServletRequest request) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename,request));
    }

}