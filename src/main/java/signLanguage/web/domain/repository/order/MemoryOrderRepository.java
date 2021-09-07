package signLanguage.web.domain.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.entity.ReceptionOrder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
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
                "from ReceptionOrder o " +
                "join o.member m " +
                "where m.id = :memberId", MainBaseInfo.class)
                .setParameter("memberId", id)
                .getResultList();

        return MainBaseInfo;
    }
}
