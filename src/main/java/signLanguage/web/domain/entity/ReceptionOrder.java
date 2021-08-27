package signLanguage.web.domain.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "reception")
@NoArgsConstructor
public class ReceptionOrder {

    @Id
    @Column(name = "reception_id")
    private String id = UUID.randomUUID().toString();

    private String subject;
    private String content;
    private 

    private


}
