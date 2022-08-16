package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class signUpForm {

    @NotBlank
    private String memberAcct;
    @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
    private String memberPw;
    @NotBlank
    private String memberPwConfirm;
    @NotBlank
    private String memberName; // 별명(닉네임)
    @Email @NotBlank
    private String memberEmail;

    // memberPw , memberPwConfirm 값을 비교하여 일치하는지 여부를 validation 하여 일치하는경우 member 객체의 memberPw 변수에 값을 담아 DB에 저장
}
