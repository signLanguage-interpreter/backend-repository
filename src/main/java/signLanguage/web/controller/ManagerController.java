package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.basicDataDto.Data;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.dto.ManagerReturnDto;
import signLanguage.web.domain.dto.UploadImage;
import signLanguage.web.domain.dto.component.FileStore;
import signLanguage.web.domain.entity.Comment;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.repository.manager.ManagerMemberRepositoryInterface;
import signLanguage.web.servie.ManagerService;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final FileStore fileStore;

    @GetMapping("/managerInfo")
    public ManagerReturnDto getManagerInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return managerService.printInterpreter(principalDetails.getMember().getId());

    }

    @PostMapping("/managerInfo")
    public void postManagerInfo(@Valid @RequestBody ManagerDto managerDto, BindingResult bindingResult,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        MultipartFile imageFile = managerDto.getImageFile();
        UploadImage uploadImage = fileStore.storeImage(imageFile);

        managerService.addInterpreter(managerDto.getIntroduce(),
                managerDto.getPosition(),
                uploadImage,
                principalDetails.getMember().getId());

    }

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }



//    @PostMapping("/addInfo")
//    public Object addManagerInfo(@Valid @RequestBody ManagerDto managerDto, BindingResult bindingResult,
//                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
//        if(bindingResult.hasErrors()){
//            return bindingResult.getAllErrors();
//        }
//        log.info("managerDto = {}" ,managerDto.toString());
//        Long addInfo = memberService.addInterpreter(principalDetails.getMember().getId(), managerDto.getPosition(), managerDto.getIntroduce(), managerDto.getImagePath());
//        return new Data<Long>(addInfo);
//    }
//
//    @PostMapping("/modifyManager/{interpreterId}")
//    public Object modifyManagerInfo(@Valid @RequestBody ManagerDto managerDto, BindingResult bindingResult, @PathVariable Long interpreterId){
//        if(!bindingResult.hasErrors()){
//            memberService.modifyInterpreter(interpreterId, managerDto.getPosition(), managerDto.getIntroduce(), managerDto.getImagePath());
//            return null;
//        }
//        return bindingResult.getAllErrors();
//    }
//
//
//    @GetMapping("/modifyManager")
//    public ManagerReturnDto getModifyManagerInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        List<Interpreter> interpreters = memberService.printInterpreter(principalDetails.getMember().getId());
//        return interpreters.stream().map(i -> new ManagerReturnDto(i)).collect(Collectors.toList()).get(0);
//    }
//
//    @lombok.Data
//    static class ManagerReturnDto {
//        private Long id;
//        private Position position;
//        private String introduce;
//        private String imagePath;
//
//        public ManagerReturnDto(Interpreter interpreter) {
//            this.id = interpreter.getId();
//            this.position = interpreter.getPosition();
//            this.introduce = interpreter.getIntroduce();
//            this.imagePath = interpreter.getImagePath();
//        }
//    }
//
//    @PostMapping("/postComment/{userId}/{receptionId}")
//    public void registryComment(@PathVariable Long userId,
//                                @PathVariable String receptionId,
//                                @RequestBody String comment){
//        orderService.registryCommentService(userId,receptionId,comment);
//    }

}