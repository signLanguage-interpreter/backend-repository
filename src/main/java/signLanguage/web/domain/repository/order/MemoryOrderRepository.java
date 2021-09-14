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


        List<Object[]> resultList = em.createNativeQuery("select X.rnum, X.RECEPTION_ID, X.RECEPTION_DATE, X.SUBJECT, X.STATUS from" +
                "(select rownum as rnum, A.RECEPTION_ID, A.RECEPTION_DATE, A.SUBJECT, A.STATUS from " +
                "(select RECEPTION_DATE, SUBJECT, STATUS, RECEPTION_ID from RECEPTION where STATUS = :status order by RECEPTION_DATE asc ) as A " +
                "where rownum <= :endnum) as X " +
                "where X.rnum >= :startnum")
                .setParameter("status", status)
                .setParameter("endnum", end)
                .setParameter("startnum", start)
                .getResultList();

        return resultList;
    }

    public Optional<ReceptionOrder> findOne(String id){
        return Optional.of(em.find(ReceptionOrder.class, id));
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
        List<RecpetionDetailDto> result = em.createQuery("select new signLanguage.web.domain.dto.RecpetionDetailDto(re.subject,re.content,re.receptionDate,re.classification,re.status, co.id, co.content, m.userNickName, co.registryTime) " +
                "from ReceptionOrder re " +
                "left join re.commentList co " +
                "left join co.member m " +
                "where re.id = :receptionId ", RecpetionDetailDto.class)
                .setParameter("receptionId", receptionId)
                .getResultList();

        log.info("{}",result);
        return result;
    }
}
