package signLanguage.web.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor

public class MemberModifyDto {
    @Email(message = "이메일 형식이 아니에요.")
    private String eMail;

    @NotNull(message = "입력된 내용이 없어요.")
    @Size(min=8, max = 20, message = "비밀번호는 8자 이상 20자 이내여야 해요.")
    private String password;

    @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$")
    private String cellPhone;
}
