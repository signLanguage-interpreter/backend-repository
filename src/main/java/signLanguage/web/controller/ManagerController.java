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
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.*;
import signLanguage.web.domain.dto.component.FileStore;
import signLanguage.web.servie.ManagerService;
import signLanguage.web.servie.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final OrderService orderService;
    private final FileStore fileStore;

    @ResponseBody
    @GetMapping("/managerInfo")
    public ManagerReturnDto getManagerInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        ManagerReturnDto managerReturnDto = managerService.printInterpreter(principalDetails.getMember().getId());
//        return managerService.printInterpreter(principalDetails.getMember().getId());
        return managerReturnDto;
    }

    @ResponseBody
    @PostMapping(value = "/managerInfo")
    public void postManagerInfo(@Valid ManagerDto managerDto, BindingResult bindingResult,
                                @AuthenticationPrincipal PrincipalDetails principalDetails,
                                HttpServletRequest request) throws IOException {
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


    @PostMapping("/receipt/{orderId}")
    public void receiptReception(@PathVariable String orderId,
                                 @RequestParam OrderStatus status,
            @AuthenticationPrincipal PrincipalDetails principalDetails)
    // 매니저는 신뢰받는 권한이기 때문에 접수에 제한로직을 걸지 않음.
    {
        if(managerService.receiptReception(orderId , principalDetails.getMember().getId(),status)){
            return ;
        }
        throw new IllegalStateException();
    }

}