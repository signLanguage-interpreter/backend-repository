package signLanguage.web.domain.repository.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
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


        List<RecpetionDetailDto> result = em.createQuery("select new signLanguage.web.domain.dto.RecpetionDetailDto(re.subject,re.content,re.receptionDate,re.classification,re.status, co.id, co.Content, m.userNickName, co.registryTime) " +
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
