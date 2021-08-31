package signLanguage.web.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import signLanguage.web.domain.common.Active;
import signLanguage.web.domain.common.CommonLocalTime;
import signLanguage.web.domain.common.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    /* basic */
    private String userNickName;
    private String username;        // 시큐리티 ID 규정상.
    private String password;


    /* optional */
    private String cellPhone;
    private String eMail;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    /* systemAdmin */
    @Builder.Default
    private String roles = "ROLE_USER";

    private LocalDate birth;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReceptionOrder> orderList = new ArrayList<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Active active = Active.ACTIVE ;

    @Embedded
    private CommonLocalTime commonLocalTime;

    @Builder
    public Member(String userNickName,
                  String username,
                  String password,
                  String cellPhone,
                  String eMail,
                  LocalDate birth,
                  Gender gender,
                  CommonLocalTime commonLocalTime) {
        this.userNickName = userNickName;
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.eMail = eMail;
        this.birth = birth;
        this.gender = gender;
        this.commonLocalTime = commonLocalTime;
    }

    public Member() {

    }

    public List<String> getRoleList(){
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public void addOrders(ReceptionOrder receptionOrder){
        if(!this.getOrderList().contains(receptionOrder)){
            this.getOrderList().add(receptionOrder);
            receptionOrder.setMember(this);
        }
    }

}
