package forum.board.controller;

import forum.board.controller.DTO.signUpForm;
import forum.board.service.signUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원가입 화면을 뿌려주는 컨트롤러
 * - 회원가입 화면
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/forum/signup")
public class signUpController {

    private final signUpService signUpService;

    @GetMapping
    public String signUpPage()
    {
        return "signUp";
    }

    @PostMapping
    public String signupProcess(@ModelAttribute signUpForm signUpForm)
    {
        if(signUpService.doSignUp(signUpForm))
        {
            return "redirect:/forum/freeBoard";
        }else{
            return "signUp";
        }

    }



}
