package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.basicDataDto.Data;
import signLanguage.web.domain.common.basicDataDto.TwoData;
import signLanguage.web.domain.dto.CommentDto;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.dto.RecpetionDetailDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.servie.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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


    @GetMapping("/main")
    public Object showMainPage(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Map<OrderService.Grouping, List<MainBaseInfo>> groupingListMap = orderService.showUserPage(principalDetails.getMember().getId());

        for (Map.Entry<OrderService.Grouping, List<MainBaseInfo>> groupingListEntry : groupingListMap.entrySet()) {
            return new TwoData<>(groupingListEntry.getKey(),groupingListEntry.getValue());
        }
        return new RuntimeException();
    }


    @GetMapping("/reception/{userId}/{receptionId}")
    public TwoData<OrderInfoDto,List<CommentDto>> showDetailReception(@PathVariable String receptionId,
                                                                      @PathVariable Long userId,
                                                                      @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getMember().getId() != userId){
            throw new RuntimeException("no authentication");
        }
        return orderService.showDetailReceptionInfo(receptionId);
    }

}
