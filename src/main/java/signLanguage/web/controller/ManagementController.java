package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.auth.PrincipalDetails;
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.dto.ManagerMainList;
import signLanguage.web.domain.dto.OrderManagerPagingDto;
import signLanguage.web.domain.dto.component.ManagerMainAllList;
import signLanguage.web.domain.dto.component.PagingComponent;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

import java.util.List;

import static signLanguage.web.servie.MemberService.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagementController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final PagingComponent pagingComponent;

    @GetMapping("/all_register") //manager/info ? page= 1 & status=HOLD
    public ManagerMainList managerMainPage(@RequestParam Long page,
                                @RequestParam OrderStatus status){
        return orderService.getManagerMainAll(page, status);
    }

    @GetMapping("/main")
    public ManagerMainAllList receptionReady(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                                                         @RequestParam Long page,
                                                                                                         @RequestParam OrderStatus status){
        if(status == OrderStatus.HOLD){
            ManagerMainAllList<OrderManagerPagingDto, List, MemberBasicInfo> orderManagerPagingDtoListMemberBasicInfoManagerMainAllList = orderService.receptionHold(page, status, principalDetails.getMember().getId());
            return orderManagerPagingDtoListMemberBasicInfoManagerMainAllList;
        }
        return orderService.receptionReady(principalDetails.getMember().getId(),page,status);
    }




//
//    //메인화면 매니저정보
//    @GetMapping("/main")
//    public MemberBasicInfo managerMain(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        return memberService.printMemberBasicInfo(principalDetails.getMember().getId());
//    }




}
