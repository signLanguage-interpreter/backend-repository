package signLanguage.web.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.CommonLocalTime;
import signLanguage.web.domain.common.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reception")
@NoArgsConstructor
@Getter
@Setter
public class ReceptionOrder {

    @Id
    @Column(name = "reception_id")
    private String id = UUID.randomUUID().toString();

    private String subject;
    private String content;

    @Enumerated(EnumType.STRING)
    private Classification classification;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private CommonLocalTime commonLocalTime; //예약 생성 일자

    private LocalDateTime receptionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receptionOrder")
    private List<Comment> commentList = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interpreter_id")
    private Interpreter interpreter;


    public void addInterpreter(Interpreter interpreter){
        if(!interpreter.getOrderList().contains(this)){
            interpreter.getOrderList().add(this);
            this.interpreter = interpreter;
        }
    }

    public void addMember(Member member){
        if(!member.getOrderList().contains(this)){
            this.member = member;
            member.getOrderList().add(this);
        }
    }



    public static ReceptionOrder createOrder(Member member,
                                             String subject,
                                             String content,
                                             Classification classification,
                                             LocalDateTime localDateTime){
        ReceptionOrder order = new ReceptionOrder();
        order.setSubject(subject);
        order.setContent(content);
        order.setClassification(classification);
        order.setCommonLocalTime(new CommonLocalTime());
        order.setReceptionDate(localDateTime);
        order.addMember(member);
//        order.addInterpreter(interpreter);
        order.setStatus(OrderStatus.HOLD);
        return order;
    }
}
