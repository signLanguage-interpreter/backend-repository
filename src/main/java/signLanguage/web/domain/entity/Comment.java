package signLanguage.web.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private LocalDateTime registryTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reception_id")
    private ReceptionOrder receptionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addMember(Member member){
        if(member != null){this.member = member;}
    }

    public void addReceptionOrder(ReceptionOrder receptionOrder){
        if(!receptionOrder.getCommentList().contains(this)){
            this.receptionOrder = receptionOrder;
            receptionOrder.getCommentList().add(this);
        }
    }

    public static Comment createComment(Member member, ReceptionOrder receptionOrder, String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.addMember(member);
        comment.addReceptionOrder(receptionOrder);
        return comment;
    }
}
