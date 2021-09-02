package signLanguage.web.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Interpreter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interpreter_id")
    private Long id;

    private String position;
    private String introduce;
}
