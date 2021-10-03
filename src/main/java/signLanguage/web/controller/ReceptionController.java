package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.basicDataDto.*;
import signLanguage.web.domain.dto.CommentDto;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.OrderInfoDto;
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
        Member member = memberDetails.getMember();
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
            if(groupingListEntry.getValue().get(0).getReceptionId() == null){
                return new TwoData<>(groupingListEntry.getKey(),null);
            }
            return new TwoData<>(groupingListEntry.getKey(),groupingListEntry.getValue());
        }
        return new RuntimeException();
    }
//     replace Two data is difference thing that basicDataInfo another Data class
//    @lombok.Data
//    static class Data<T>{
//        private T user;
//        public Data(T user) {
//            this.user = user;
//        }
//    }


    @GetMapping("/regist/{userId}/{receptionId}")
    public List<OrderInfoDto> showDetailReception(@PathVariable String receptionId,
                                                                      @PathVariable Long userId,
                                                                      @AuthenticationPrincipal PrincipalDetails principalDetails){

        List<OrderInfoDto> orderInfoDtos = orderService.showDetailReceptionInfo(receptionId);
        return orderInfoDtos;
    }


}
