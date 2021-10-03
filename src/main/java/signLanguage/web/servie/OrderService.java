package signLanguage.web.servie;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.common.basicDataDto.TwoData;
import signLanguage.web.domain.dto.*;
import signLanguage.web.domain.dto.component.ManagerMainAllList;
import signLanguage.web.domain.dto.component.PagingComponent;
import signLanguage.web.domain.entity.Member;
import signLanguage.web.domain.entity.ReceptionOrder;
import signLanguage.web.domain.repository.member.MemberRepositoryInterface;
import signLanguage.web.domain.repository.order.OrderRepositoryInterface;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static signLanguage.web.servie.MemberService.MemberBasicInfo;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepositoryInterface orderRepository;
    private final MemberRepositoryInterface memberRepository;
    private final PagingComponent pagingComponent;

    public ManagerMainList<OrderManagerPagingDto, List> getManagerMainAll(Long curPage ,OrderStatus status){
        OrderManagerPagingDto pagingDto = getOrderManagerPaging(curPage, status);
        List<Object[]> allInfo = orderRepository.findAll(pagingComponent.getStart(curPage),pagingComponent.getEnd(curPage),status);

        if(allInfo.isEmpty()){
            return new ManagerMainList<>(pagingDto, null);
        }

//        if(allInfo.get(0)[0]==null){
//            return new ManagerMainList<>(pagingDto, null);
//        }
        List<MainBaseInfo> collect = allInfo.stream().map(
                a -> new MainBaseInfo( Long.valueOf(a[0].toString()),
                        (String) a[1],
                        localDateTimeFromTimestamp((Timestamp) a[2]),
                        (String) a[3],
                        OrderStatus.valueOf((String) a[4]),
                        Classification.valueOf((String) a[5]))).collect(toList());

        return new ManagerMainList<>(pagingDto, collect);
    }


    public ManagerMainAllList<OrderManagerPagingDto, List, MemberBasicInfo> receptionReady(Long id, Long curPage, OrderStatus status) {
        OrderManagerPagingDto pagingDto = getOrderManagerPaging(curPage, status);
        List<ReceptionOrder> interpreterJoinOrder = orderRepository.findInterpreterJoinOrder(id, pagingDto.getStart(), pagingDto.getEnd(),status);
        List<MainBaseInfo> collect = interpreterJoinOrder.stream().map(o -> new MainBaseInfo(
                o.getMember().getId(),
                o.getId(),
                o.getReceptionDate(),
                o.getSubject(),
                o.getStatus(),
                o.getClassification(),
                o.getMember().getUserNickName())).collect(toList());

        if(collect.isEmpty()){
            Member member = memberRepository.findOne(id).orElseThrow(() -> new NullPointerException());
            MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getId(),
                    member.getUserNickName(),
                    member.getUsername(),
                    member.getEMail(),
                    member.getCellPhone());
            return new ManagerMainAllList<>(null, null, memberBasicInfo);
        }

        Member member = interpreterJoinOrder.get(0).getInterpreter().getMember();
        MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getId(),
                member.getUserNickName(),
                member.getUsername(),
                member.getEMail(),
                member.getCellPhone());


        return new ManagerMainAllList<>(pagingDto, collect, memberBasicInfo);
    }

    private OrderManagerPagingDto getOrderManagerPaging(Long curPage, OrderStatus status) {
        Long allAmount = orderRepository.findAllCount(status);
        OrderManagerPagingDto pagingDto = new OrderManagerPagingDto(pagingComponent.getStart(curPage),
                pagingComponent.getEnd(curPage),
                pagingComponent.getStartPage(curPage, allAmount),
                pagingComponent.getEndPage(curPage, allAmount),
                pagingComponent.getRealEnd(allAmount),
                pagingComponent.getStartPageExist(curPage, allAmount),
                pagingComponent.getEndPageExist(curPage, allAmount));
        return pagingDto;
    }

    protected LocalDateTime localDateTimeFromTimestamp(Timestamp timestamp){
        return LocalDateTime
                .ofInstant(timestamp.toInstant(), ZoneOffset.ofHours(0));
    }


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


    public List<OrderInfoDto> showDetailReceptionInfo(String receptionId) {
        List<RecpetionDetailDto> findReceptionOrders = orderRepository.findOneWithComment(receptionId);


        List<OrderInfoDto> collect = findReceptionOrders.stream().collect(groupingBy((o) -> new OrderInfoDto(o.getStatus(), o.getSubject(), o.getContent(), o.getReceptionDate(), o.getClassification()),
                mapping((o) -> new CommentDto(o.getContentId(), o.getContent(), o.getUserNickName(), o.getRegistryTime()), toList())))
                .entrySet().stream()
                .map(e -> new OrderInfoDto(e.getKey().getOrderStatus(), e.getKey().getSubject(), e.getKey().getContent(), e.getKey().getReceptionDate(), e.getKey().getClassification(), e.getValue()))
                .collect(toList());
        return collect;

//        log.info("=========================사이즈{}",findReceptionOrders.size());
//        if(findReceptionOrders.isEmpty()){
//            throw new NullPointerException();
//        }
//        ReceptionOrder receptionOrder = findReceptionOrders.get(0);
//        OrderInfoDto orderInfoDto = new OrderInfoDto(receptionOrder.getStatus(), receptionOrder.getSubject(), receptionOrder.getContent(), receptionOrder.getReceptionDate(), receptionOrder.getClassification());
//
//        if(receptionOrder.getCommentList().isEmpty()){
//            return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDto,null);
//        }
//        List<CommentDto> collect = receptionOrder.getCommentList().stream().map(c -> new CommentDto(c.getId(), c.getContent(), c.getMember().getUserNickName(), c.getRegistryTime())).collect(toList());
//        return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDto,collect);



//        for (Map.Entry<OrderInfoDto, List<CommentDto>> orderInfoDtoListEntry : collect.entrySet()) {
//            if(orderInfoDtoListEntry.getValue().get(0).getId()==null){
//                return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDtoListEntry.getKey(),null);
//            }
//            int size = orderInfoDtoListEntry.getValue().size();
//            log.info("=========================사이즈{}",size);
//            return new TwoData<OrderInfoDto,List<CommentDto>>(orderInfoDtoListEntry.getKey(),orderInfoDtoListEntry.getValue());
//
//        }
//        return null;
    }

    public ManagerMainAllList<OrderManagerPagingDto, List, MemberBasicInfo> receptionHold(Long page, OrderStatus status, Long managerId) {
        OrderManagerPagingDto pagingDto = getOrderManagerPaging(page, status);
        List<ReceptionOrder> hold = orderRepository.findHold(pagingDto.getStart(), pagingDto.getEnd(), status);
        Member member = memberRepository.findOne(managerId).orElseThrow(() -> new NullPointerException());

        MemberBasicInfo memberBasicInfo = new MemberBasicInfo(member.getId(),
                member.getUserNickName(),
                member.getUsername(),
                member.getEMail(),
                member.getCellPhone());

        List<MainBaseInfo> collect = hold.stream().map(o -> new MainBaseInfo(
                o.getMember().getId(),
                o.getId(),
                o.getReceptionDate(),
                o.getSubject(),
                o.getStatus(),
                o.getClassification(),
                o.getMember().getUserNickName())).collect(toList());

        if(collect.isEmpty()){
            return new ManagerMainAllList<>(null, null, memberBasicInfo);
        }

        return new ManagerMainAllList<>(pagingDto,collect,memberBasicInfo);

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