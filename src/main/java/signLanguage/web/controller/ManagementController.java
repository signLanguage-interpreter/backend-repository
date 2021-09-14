package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.common.basicDataDto.TwoData;
import signLanguage.web.domain.dto.ManagerMainList;
import signLanguage.web.domain.dto.OrderManagerPagingDto;
import signLanguage.web.domain.dto.component.PagingComponent;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
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
    public ManagerMainList managerMain(){
        return null;
    }




}
