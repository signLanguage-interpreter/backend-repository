package signLanguage.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinDto {

    @NotNull(message = "입력된 내용이 없어요.")
    @Size(max = 20, message = "이름은 20자 이내여야 해요.")
    private String userNickName;

    @NotNull(message = "입력된 내용이 없어요.")
    @Size(min=7, max=20, message = "ID는 7자 이상 20자 이내여야 해요.")
    private String username;

    @Email(message = "이메일 형식이 아니에요.")
    private String eMail;

    @NotNull(message = "입력된 내용이 없어요.")
    @Size(min=8, max = 20, message = "비밀번호는 8자 이상 20자 이내여야 해요.")
    private String password;

    @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$")
    private String cellPhone;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$" )
    @NotNull
    private String birth;

    //true(남) false(여)
    private boolean gender;


    public MemberJoinDto(@NotNull(message = "입력된 내용이 없어요.") @Size(max = 20, message = "이름은 20자 이내여야 해요.") String userNickName, @NotNull(message = "입력된 내용이 없어요.") @Size(min = 7, max = 20, message = "ID는 7자 이상 20자 이내여야 해요.") String username, @Email(message = "이메일 형식이 아니에요.") String eMail, @NotNull(message = "입력된 내용이 없어요.") @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이내여야 해요.") String password, @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$") String cellPhone, @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$") @NotNull String birth, boolean gender) {
        this.userNickName = userNickName;
        this.username = username;
        this.eMail = eMail;
        this.password = password;
        this.cellPhone = cellPhone;
        this.birth = birth;
        this.gender = gender;
    }

    public MemberJoinDto(@NotNull(message = "입력된 내용이 없어요.") @Size(max = 20, message = "이름은 20자 이내여야 해요.") String userNickName, @NotNull(message = "입력된 내용이 없어요.") @Size(min = 7, max = 20, message = "ID는 7자 이상 20자 이내여야 해요.") String username, @Email(message = "이메일 형식이 아니에요.") String eMail, @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$") String cellPhone, @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$") @NotNull String birth, boolean gender) {
        this.userNickName = userNickName;
        this.username = username;
        this.eMail = eMail;
        this.cellPhone = cellPhone;
        this.birth = birth;
        this.gender = gender;
    }
}
