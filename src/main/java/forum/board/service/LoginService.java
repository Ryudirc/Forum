package forum.board.service;

import forum.board.controller.DTO.loginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {


    public Boolean doLogin(loginForm form)
    {
        // 로그인 폼 정보를 받아와서 validation 을 수행하고 결과값이 참이면 true 를 리턴하고, 결과값이 참이 아니면 false 를 리턴한다.
        if(true)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
