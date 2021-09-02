package signLanguage.web.domain.entity;

import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import signLanguage.web.domain.common.Position;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Position position;
    private String introduce;
    private String imagePath;

//
//    @OneToMany(mappedBy = "interpreter", fetch = FetchType.LAZY, cascade = P)
//    private List<ReceptionOrder> orderList = new ArrayList<>();
//
    @OneToOne(mappedBy = "interpreter", fetch = FetchType.LAZY)
    private Member member;

    public static Interpreter createAddInfo(Position position, String introduce, String imagePath, Member member){
        Interpreter interpreter = new Interpreter();
        interpreter.setImagePath(imagePath);
        interpreter.setIntroduce(introduce);
        interpreter.setPosition(position);
        member.addInterpreter(interpreter);

        return interpreter;
    }

}
