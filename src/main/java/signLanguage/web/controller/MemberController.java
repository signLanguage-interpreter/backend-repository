package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.CommonLocalTime;
import signLanguage.web.domain.common.Gender;
import signLanguage.web.domain.dto.CommentDto.ReturnComment;
import signLanguage.web.domain.dto.MemberJoinDto;
import signLanguage.web.domain.dto.MemberModifyDto;
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

        if (validationBindingResult(bindingResult)) return bindingResult.getAllErrors();

        Long joinMemberId = memberService.join(member);
        log.info("가입을 진행한 Member = {}", joinMemberId);

        return joinMemberId;
    }




    @PostMapping("/user/modifyMember/{userId}")
    public Object modifyMember(@PathVariable Long userId,
                               @Valid @RequestBody MemberModifyDto memberModifyDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails principalDetails){
        validationMember(userId, principalDetails);
        if (validationBindingResult(bindingResult)){
            return bindingResult.getAllErrors();
        }
        memberService.modifyMember(userId, memberModifyDto.getEMail(), bCryptPasswordEncoder.encode(memberModifyDto.getPassword()), memberModifyDto.getCellPhone());
        return null;
    }

    private boolean validationBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return true;
        }
        return false;
    }

    private void validationMember(@PathVariable Long userId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getMember().getId() != userId) {
            throw new RuntimeException("비정상적인 접근입니다.");
        }
    }

    @PostMapping("/user/{orderId}/comment")
    public void registryComment(@Valid ReturnComment commentDto,
                                @PathVariable String orderId,
                                @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("=============={}",commentDto.getContent());
        memberService.regComment(commentDto,principalDetails.getMember().getId(),orderId);

    }


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    @GetMapping("/user/modifyMember/{userId}")
    public MemberJoinDto modifyForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                           @PathVariable Long userId){

        validationMember(userId, principalDetails);
        Member findMember = memberService.findById(userId);
        return new MemberJoinDto(findMember.getUserNickName(), findMember.getUsername(), findMember.getEMail(), findMember.getCellPhone(), findMember.getBirth().toString(),findMember.getGender()==Gender.MAN?true:false);

    }



    //============================================================




    @PostMapping("/managerJoin")
    public Object joinManager(@Valid @RequestBody MemberJoinDto memberJoinDto,
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
        member.setRoles("ROLE_MANAGER");

        if (validationBindingResult(bindingResult)) return bindingResult.getAllErrors();

        Long joinMemberId = memberService.join(member);
        log.info("가입을 진행한 Manager = {}", joinMemberId);

        return joinMemberId;
    }





}
