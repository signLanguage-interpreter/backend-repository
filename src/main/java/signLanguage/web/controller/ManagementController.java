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
import signLanguage.web.domain.dto.component.PagingComponent;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagementController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final PagingComponent pagingComponent;

    @GetMapping("") //manager/info ? page= 1 & status=HOLD
    public ManagerMainList managerMainPage(@RequestParam Long page,
                                @RequestParam OrderStatus status){
        return orderService.getManagerMainAll(page, status);
    }

    @GetMapping("/recepten")
    public ManagerMainList<OrderManagerPagingDto, List> receptionReady(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                       @RequestParam Long page,
                                                                       @RequestParam OrderStatus status){
        return orderService.receptionReady(principalDetails.getMember().getInterpreter().getId(),page,status);
    }

    //메인화면 매니저정보
    @GetMapping("/main")
    public MemberService.MemberBasicInfo managerMain(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return memberService.printMemberBasicInfo(principalDetails.getMember().getId());
    }




}
