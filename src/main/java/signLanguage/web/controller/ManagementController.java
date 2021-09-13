package signLanguage.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import signLanguage.web.domain.repository.order.MemoryOrderRepository;
import signLanguage.web.servie.MemberService;
import signLanguage.web.servie.OrderService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ManagementController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final MemoryOrderRepository memoryOrderRepository;

    @GetMapping("/main")
    public void managerMainPage(@RequestParam Long start,
                                @RequestParam Long end){

    }



}
