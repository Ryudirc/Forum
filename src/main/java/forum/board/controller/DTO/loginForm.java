package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
public class loginForm {

    private String memberAcct;
    private String memberPw;

}
