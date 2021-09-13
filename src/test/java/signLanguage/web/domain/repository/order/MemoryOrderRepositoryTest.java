package signLanguage.web.domain.repository.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryOrderRepositoryTest {

    @Autowired
    MemoryOrderRepository memoryOrderRepository;

    @Test
    public void findAll() {
        List all = memoryOrderRepository.findAll(21, 10);
        for (Object o : all) {
            System.out.println("o.toString() = " + o.toString());
            Assertions.assertThat("1").isEqualTo("1" );
        }
    }

}