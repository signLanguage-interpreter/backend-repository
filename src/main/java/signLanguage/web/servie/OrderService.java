package signLanguage.web.servie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.member.MemberRepositoryInterface;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepositoryInterface orderRepository;
    private final MemberRepositoryInterface memberRepository;

    @Transactional
    public String saveOrder(Long memberId,
                            String subject,
                            String content,
                            Classification classification,
                            LocalDateTime localDateTime){

        Optional<Member> memberOptional = memberRepository.findOne(memberId);
        Member member = memberOptional.orElseThrow(() -> new NullPointerException("NULL 존재"));

        ReceptionOrder order = ReceptionOrder.createOrder(member, subject, content, classification, localDateTime);
        return orderRepository.save(order);
    }

    public Map<Grouping, List<MainBaseInfo>> showUserPage(Long id){
        Function<MainBaseInfo, Grouping> classifier = Grouping::new;
        return orderRepository.findMemberJoinOrder(id).stream().collect(Collectors.groupingBy(classifier));
    }


    public void showDetailReceptionInfo(String receptionId) {
        orderRepository.findOneWithComment(receptionId);
    }

    @Data
    public static class Grouping{
        private Long id;
        private String userNickName;
        private String username;
        private String eMail;

        public Grouping(MainBaseInfo mainBaseInfo) {
            this.id = mainBaseInfo.getId();
            this.userNickName = mainBaseInfo.getUserNickName();
            this.username = mainBaseInfo.getUsername();
            this.eMail = mainBaseInfo.getEMail();
        }
    }

}