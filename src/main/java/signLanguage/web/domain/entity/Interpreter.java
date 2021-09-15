package signLanguage.web.domain.entity;

import com.sun.xml.bind.v2.runtime.unmarshaller.Intercepter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import signLanguage.web.domain.common.Position;
import signLanguage.web.domain.common.UploadName;
import signLanguage.web.domain.dto.UploadImage;

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

    @Enumerated(EnumType.STRING)
    private Position position;

    private String introduce;

    @Embedded
    private UploadName uploadName;


    @OneToMany(mappedBy = "interpreter", fetch = FetchType.LAZY)
    private List<ReceptionOrder> orderList = new ArrayList<>();

    @OneToOne(mappedBy = "interpreter", fetch = FetchType.LAZY)
    private Member member;


    public static Interpreter createInterpreter(String introduce, Position position, UploadName uploadName,Member member){
        Interpreter interpreter = new Interpreter();
        interpreter.setIntroduce(introduce);
        interpreter.setPosition(position);
        interpreter.setUploadName(uploadName);
        member.addInterpreter(interpreter);
        return interpreter;
    }

}