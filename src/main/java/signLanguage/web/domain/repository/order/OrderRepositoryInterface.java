package signLanguage.web.domain.repository.order;

import signLanguage.web.domain.common.OrderStatus;
import signLanguage.web.domain.dto.MainBaseInfo;
import signLanguage.web.domain.dto.RecpetionDetailDto;
import signLanguage.web.domain.entity.ReceptionOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryInterface {
    public String save(ReceptionOrder order);
    public Optional<ReceptionOrder> findOne(String id);
    public List<MainBaseInfo> findMemberJoinOrder(Long id);
    public List<RecpetionDetailDto> findOneWithComment(String receptionId);
    public Long findAllCount(OrderStatus status);
    public List findAll(Long start, Long end, OrderStatus status);
}
