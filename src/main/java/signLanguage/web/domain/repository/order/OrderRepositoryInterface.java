package signLanguage.web.domain.repository.order;

import signLanguage.web.domain.entity.ReceptionOrder;

import java.util.Optional;

public interface OrderRepositoryInterface {
    public String save(ReceptionOrder order);
    public Optional<ReceptionOrder> findOne(String id);
}
