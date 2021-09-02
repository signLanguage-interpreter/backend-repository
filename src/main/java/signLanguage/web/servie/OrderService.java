package signLanguage.web.servie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.member.MemberRepositoryInterface;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.time.LocalDateTime;
import java.util.Optional;

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

}
