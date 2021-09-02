package signLanguage.web.controller;

import jdk.nashorn.api.tree.ArrayLiteralTree;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.Data;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.servie.OrderService;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            return new Data<String>(orderService.saveOrder(member.getId(),
                                    orderInfoDto.getSubject(),
                                    orderInfoDto.getContent(),
                                    orderInfoDto.getClassification(),
                                    orderInfoDto.getReceptionDate()));
        }
        return bindingResult.getAllErrors();
    }


    @GetMapping("/reception")
    public ReceptionBaseInfo formReception(@AuthenticationPrincipal PrincipalDetails memberDetails){
        Member member = memberDetails.getMember();
        return new ReceptionBaseInfo(member.getId(), member.getUserNickName(), member.getCellPhone());
    }

    @lombok.Data
    @AllArgsConstructor
    static class ReceptionBaseInfo{
        private Long id;
        private String userNickName;
        private String cellPhone;
    }





    @GetMapping("/test")
    public void formReception(){

    }

}
