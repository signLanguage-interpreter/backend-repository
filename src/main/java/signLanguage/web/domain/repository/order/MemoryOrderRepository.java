package signLanguage.web.domain.repository.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.RecpetionDetailDto;
import signLanguage.web.domain.entity.ReceptionOrder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemoryOrderRepository implements OrderRepositoryInterface {

    private final EntityManager em;

    public String save(ReceptionOrder order){
        em.persist(order);
        return order.getId();
    }

    public Long findAllCount(OrderStatus status){
        return em.createQuery("select count(o) from ReceptionOrder o where o.status=:status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }

    public List findAll(Long start, Long end, OrderStatus status){
        /* 성능 문제 개선*/
        //        List<OrderManagerDto> resultList = em.createQuery("select new signLanguage.web.domain.dto.OrderManagerDto(o.id,o.subject,o.receptionDate,o.status,count(o)) " +
//                "from ReceptionOrder o " +
//                "where o.status = :sub " +
//                "order by o.receptionDate", OrderManagerDto.class)
//                .setParameter("sub", OrderStatus.HOLD)
//                .setFirstResult(start)
//                .setMaxResults(end)
//                .getResultList();
//        return resultList;

        List<Object[]> resultList = em.createNativeQuery("select X.rnum, X.RECEPTION_ID, X.RECEPTION_DATE, X.SUBJECT, X.STATUS, X.CLASSIFICATION from" +
                "(select rownum as rnum, A.RECEPTION_ID, A.RECEPTION_DATE, A.SUBJECT, A.STATUS, A.CLASSIFICATION from " +
                "(select RECEPTION_DATE, SUBJECT, STATUS, RECEPTION_ID, CLASSIFICATION from RECEPTION where STATUS = :status order by RECEPTION_DATE asc ) as A " +
                "where rownum <= :endnum) as X " +
                "where X.rnum >= :startnum")
                .setParameter("status", status.toString())
                .setParameter("endnum", end)
                .setParameter("startnum", start)
                .getResultList();

        return resultList;
    }

    public Optional<ReceptionOrder> findOne(String id){
        return Optional.of(em.find(ReceptionOrder.class, id));
    }



    //find ready or end
    public List<ReceptionOrder> findInterpreterJoinOrder(Long memberId,Long start, Long end,OrderStatus status){
        List<ReceptionOrder> result = em.createQuery("select o from ReceptionOrder o " +
                "join fetch o.interpreter i " +
                "join fetch i.member m where m.id =:memberId and o.status =:status " +
                        "order by o.receptionDate asc "
                , ReceptionOrder.class)
                .setParameter("memberId",  memberId)
                .setParameter("status",status)
                .setFirstResult(Long.valueOf(start).intValue()-1)
                .setMaxResults(Long.valueOf(end).intValue())
                .getResultList();
        return result;
    }

    @Override
    public List<ReceptionOrder> findHold(Long start, Long end, OrderStatus status) {
        List<ReceptionOrder> receptionHold = em.createQuery("select o from ReceptionOrder o join fetch o.member where o.status = :status " +
                        "order by o.receptionDate asc"
                , ReceptionOrder.class)
                .setParameter("status", status)
                .setFirstResult(Long.valueOf(start).intValue()-1)
                .setMaxResults(Long.valueOf(end).intValue())
                .getResultList();

        log.info("{}",receptionHold.size());

        return receptionHold;
    }

    public List<MainBaseInfo> findMemberJoinOrder(Long id){
        List<MainBaseInfo> MainBaseInfo = em.createQuery("select new signLanguage.web.domain.dto.MainBaseInfo(m.id,m.userNickName,m.cellPhone,m.username,m.eMail,o.id,o.status,o.classification,o.subject,o.receptionDate) " +
                "from Member m  " +
                "left join m.orderList o " +
                "where m.id = :memberId", MainBaseInfo.class)
                .setParameter("memberId", id)
                .getResultList();

        return MainBaseInfo;
    }

    @Override
    public List<RecpetionDetailDto> findOneWithComment(String receptionId) {

        return em.createQuery("select new signLanguage.web.domain.dto.RecpetionDetailDto(o.subject,o.content,o.receptionDate,o.classification,o.status,c.id,c.content,m.userNickName, c.registryTime) " +
                "from ReceptionOrder o " +
                "join o.commentList c " +
                "join c.member m " +
                "where o.id = :receptionId", RecpetionDetailDto.class).setParameter("receptionId",receptionId).getResultList();

//        List<ReceptionOrder> receptionOrders = em.createQuery("select re " +
//                "from ReceptionOrder re " +
//                "left join Comment co " +
//                "on re.id = co.receptionOrder.id " +
//                "left join co.member m " +
//                "on m.id = co.member.id " +
//                        "where re.id = :receptionId"
//                , ReceptionOrder.class)
//                .setParameter("receptionId", receptionId)
//                .getResultList();
    }
}
