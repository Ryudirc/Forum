package forum.board.controller;

import forum.board.controller.DTO.LoginForm;
import forum.board.domain.LoginMember;
import forum.board.domain.Member;
import forum.board.global.SessionConst;
import forum.board.service.LoginService;
import forum.board.validation.LoginValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 로그인 컨트롤러
 * - 로그인화면 노출, 로그인 및 로그아웃 처리를 담당
 */

@Controller
@RequestMapping("/bgshop")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final LoginValidator loginValidator;


    @GetMapping("/login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm loginForm)
    {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute("loginForm") LoginForm loginform, BindingResult bindingResult, HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL)
    {
        // 로그인 시도 시 아아디 및 비밀번호 검증
        if(loginValidator.supports(loginform.getClass()))
        {
            loginValidator.validate(loginform,bindingResult);
            if(bindingResult.hasErrors()) {
                return "login";
            }
        }

        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성(회원정보 세션에 담아서)
        Member loginMember = loginService.doLogin(loginform);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,new LoginMember(loginMember.getMemberId(),loginMember.getMemberName(),loginMember.getRole()));

        return "redirect:" + redirectURL;

    }

    @PostMapping("/logout")
    public String logoutProcess(HttpServletRequest request)
    {
        //세션이 있으면 기존 세션을 가져오고 없으면 새로 생성해야 하는데, create 속성의 값이 false 이므로 없어도 새로 생성하지않음
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            session.invalidate(); //세션값과 해당 데이터 모두 삭제됨
        }
        return "redirect:/";
    }



}
