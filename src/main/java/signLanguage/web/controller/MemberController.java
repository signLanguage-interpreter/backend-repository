package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.domain.common.CommonLocalTime;
import signLanguage.web.domain.common.Gender;
import signLanguage.web.domain.dto.MemberJoinDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.servie.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public Object joinMember(@Valid @RequestBody MemberJoinDto memberJoinDto,
                           BindingResult bindingResult, HttpServletRequest request) {

        Member member = Member.builder()
                .birth(LocalDate.parse(memberJoinDto.getBirth(), DateTimeFormatter.ISO_DATE))
                .cellPhone(memberJoinDto.getCellPhone())
                .eMail(memberJoinDto.getEMail())
                .gender(memberJoinDto.isGender() ? Gender.MAN : Gender.WOMAN)
                .username(memberJoinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()))
                .userNickName(memberJoinDto.getUserNickName())
                .commonLocalTime(new CommonLocalTime()).build();

        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }

        Long joinMemberId = memberService.join(member);
        log.info("가입을 진행한 Member = {}", joinMemberId);

        return joinMemberId;
    }




    //===============================test =================================================
    @PostMapping("/managerJoin")
    public Object joinManager(@Valid @RequestBody MemberJoinDto memberJoinDto,
                             BindingResult bindingResult){

        Member member = Member.builder()
                .birth(LocalDate.parse(memberJoinDto.getBirth(), DateTimeFormatter.ISO_DATE))
                .cellPhone(memberJoinDto.getCellPhone())
                .eMail(memberJoinDto.getEMail())
                .gender(memberJoinDto.isGender()? Gender.MAN: Gender.WOMAN)
                .username(memberJoinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(memberJoinDto.getPassword()))
                .userNickName(memberJoinDto.getUserNickName())
                .commonLocalTime(new CommonLocalTime())
                .build();

        member.setRoles("ROLE_MANAGER");

        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors();
        }

        Long joinMemberId = memberService.join(member);
        log.info("가입을 진행한 Manager = {}",joinMemberId);
        return joinMemberId;
    }



}
