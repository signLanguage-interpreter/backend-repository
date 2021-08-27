package signLanguage.web.domain.common;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Embeddable
public class CommonLocalTime {
    private LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime endDateTime;

}
