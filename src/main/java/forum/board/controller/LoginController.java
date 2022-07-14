package forum.board.controller;

import forum.board.controller.DTO.loginForm;
import forum.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로그인 화면을 뿌려주는 컨트롤러
 * - 로그인 화면
 */

@Controller
@RequestMapping("/forum/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginPage()
    {
        return "login";
    }

    @PostMapping
    public String loginProcess(@ModelAttribute loginForm loginform)
    {
        if(loginService.doLogin(loginform))
        {
            return "redirect:/forum/freeBoard";
        }
        else {
            return "login";
        }
    }



}
