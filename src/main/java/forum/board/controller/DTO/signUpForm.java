package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class signUpForm {

    private String memberAcct;
    private String memberPw;
    private String memberName;
    private String memberEmail;
}
