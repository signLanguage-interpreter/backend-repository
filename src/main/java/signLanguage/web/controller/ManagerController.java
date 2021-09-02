package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.Data;
import signLanguage.web.domain.dto.ManagerDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.repository.member.MemberRepositoryInterface;
import signLanguage.web.servie.MemberService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final MemberService memberService;

    @PostMapping("/addInfo")
    public Object addManagerInfo(@Valid @RequestBody ManagerDto managerDto, BindingResult bindingResult,
                               @AuthenticationPrincipal Member member){
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors();
        }
        Long addInfo = memberService.addInterpreter(member.getId(), managerDto.getPosition(), managerDto.getIntroduce(), managerDto.getImagePath());
        return new Data<Long>(addInfo);
    }
}
