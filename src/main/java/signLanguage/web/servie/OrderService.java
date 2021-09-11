package signLanguage.web.servie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.basicDataDto.TwoData;
import signLanguage.web.domain.dto.CommentDto;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.OrderInfoDto;
import signLanguage.web.domain.dto.RecpetionDetailDto;
import signLanguage.web.domain.entity.Comment;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.comment.CommentRepositoryInterface;
import signLanguage.web.domain.repository.member.MemberRepositoryInterface;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.yaml.snakeyaml.nodes.NodeId.mapping;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepositoryInterface orderRepository;
    private final MemberRepositoryInterface memberRepository;
    private final CommentRepositoryInterface commentRepository;

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
        return orderRepository.findMemberJoinOrder(id).stream().collect(groupingBy(classifier));
    }


    public TwoData<OrderInfoDto,List<CommentDto>> showDetailReceptionInfo(String receptionId) {
        List<RecpetionDetailDto> oneWithComment = orderRepository.findOneWithComment(receptionId);
        Map<OrderInfoDto, List<CommentDto>> collect = oneWithComment.stream()
                .collect(groupingBy(o -> new OrderInfoDto(o.getStatus(), o.getSubject(), o.getContent(), o.getReceptionDate(), o.getClassification()),
                        Collectors.mapping(o -> new CommentDto(o.getCommentId(), o.getUserNickName(), o.getUserNickName(), o.getRegistryTime()), toList())));
        for (Map.Entry<OrderInfoDto, List<CommentDto>> orderInfoDtoListEntry : collect.entrySet()) {
            if(orderInfoDtoListEntry.getValue().get(0).getId()==null){
                return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDtoListEntry.getKey(),null);
            }
            return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDtoListEntry.getKey(),orderInfoDtoListEntry.getValue());
        }
        return null;
    }

    @Transactional
    public void registryCommentService(Long userId, String receptionId, String comment){
        Member member = memberRepository.findOne(userId).get();
        ReceptionOrder receptionOrder = orderRepository.findOne(receptionId).get();
        Comment commentEntity = Comment.createComment(member, receptionOrder, comment);
        commentRepository.save(commentEntity);
    }

    @Data
    public static class Grouping{
        private Long id;
        private String userNickName;
        private String username;
        private String eMail;
        private String cellPhone;

        public Grouping(MainBaseInfo mainBaseInfo) {
            this.id = mainBaseInfo.getId();
            this.cellPhone = mainBaseInfo.getCellPhone();
            this.userNickName = mainBaseInfo.getUserNickName();
            this.username = mainBaseInfo.getUsername();
            this.eMail = mainBaseInfo.getEMail();
        }
    }

}