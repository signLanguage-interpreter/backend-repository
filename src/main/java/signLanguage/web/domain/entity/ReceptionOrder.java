package signLanguage.web.domain.entity;

import lombok.NoArgsConstructor;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.CommonLocalTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reception")
@NoArgsConstructor
public class ReceptionOrder {

    @Id
    @Column(name = "reception_id")
    private String id = UUID.randomUUID().toString();

    private String username;
    private String cellPhone;

    private String subject;
    private String content;
    private Classification classification;

    @Embedded
    private CommonLocalTime commonLocalTime;

    @ManyToOne
    @
    private Member member;


}
