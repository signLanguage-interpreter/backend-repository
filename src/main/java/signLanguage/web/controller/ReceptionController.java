package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.servie.OrderService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class ReceptionController {

    private final OrderService orderService;

    @PostMapping("/reception")
    public Object orderReception(@Valid @RequestBody OrderInfoDto orderInfoDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal PrincipalDetails memberDetails){
        Member member = memberDetails.getMember(); // 준영속 ?
        log.info("id가 ({})인 멤버의 예약을 시도합니다. = [{}]",member.getId(),member.toString());
        if (!bindingResult.hasErrors()){
            return orderService.saveOrder(member.getId(), orderInfoDto.getSubject(), orderInfoDto.getContent(), orderInfoDto.getClassification(), orderInfoDto.getReceptionDate());
        }
        return bindingResult.getAllErrors();
    }

}
