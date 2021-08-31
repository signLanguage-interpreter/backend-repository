package signLanguage.web.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import signLanguage.web.domain.common.Classification;
import signLanguage.web.domain.common.CommonLocalTime;
import signLanguage.web.domain.common.OrderStatus;

import javax.persistence.*;
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
    private CommonLocalTime commonLocalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


//    public void addMember(Member member){
//        if(!member.getOrderList().contains(this)){
//            this.member = member;
//            member.getOrderList().add(this);
//        }
//    }

    public static ReceptionOrder createOrder(Member member){
        ReceptionOrder order = new ReceptionOrder();
//        order.setSubject();
//        order.setContent();
//        order.setClassification();
        order.setStatus(OrderStatus.HOLD);
        return order;
    }
}
