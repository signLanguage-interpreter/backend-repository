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
import signLanguage.web.domain.dto.component.PagingComponent;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagementController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final PagingComponent pagingComponent;

    @GetMapping("/test")
    public ManagerMainList managerMainPage(@RequestParam Long page,
                                @RequestParam OrderStatus status){
        return orderService.getManagerMainAll(page, status);
    }

    //메인화면 매니저정보
    @GetMapping("/test1")
    public MemberService.MemberBasicInfo managerMain(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return memberService.printMemberBasicInfo(principalDetails.getMember().getId());
    }




}
