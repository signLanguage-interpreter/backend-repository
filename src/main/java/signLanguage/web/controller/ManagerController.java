package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.basicDataDto.Data;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.entity.Comment;
import signLanguage.web.domain.entity.Interpreter;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final MemberService memberService;
    private final OrderService orderService;

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

    @PostMapping("/postComment/{userId}/{receptionId}")
    public void registryComment(@PathVariable Long userId,
                                @PathVariable String receptionId,
                                @RequestBody String comment){
        orderService.registryCommentService(userId,receptionId,comment);
    }

}