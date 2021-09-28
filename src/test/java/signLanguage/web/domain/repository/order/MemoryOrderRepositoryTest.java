package signLanguage.web.domain.repository.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import signLanguage.web.domain.entity.ReceptionOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemoryOrderRepositoryTest {

    @Autowired MemoryOrderRepository orderRepository;
    
    @Test
    void findInterpreterJoinOrder() {

    }
}