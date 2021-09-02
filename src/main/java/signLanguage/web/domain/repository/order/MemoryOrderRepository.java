package signLanguage.web.domain.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import signLanguage.web.domain.entity.ReceptionOrder;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemoryOrderRepository implements OrderRepositoryInterface {

    private final EntityManager em;

    public String save(ReceptionOrder order){
        em.persist(order);
        return null;
    }

    public Optional<ReceptionOrder> findOne(String id){
        return Optional.of(em.find(ReceptionOrder.class, id));
    }

}
