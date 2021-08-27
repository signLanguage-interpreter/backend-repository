package signLanguage.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import signLanguage.web.domain.common.Gender;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
}
