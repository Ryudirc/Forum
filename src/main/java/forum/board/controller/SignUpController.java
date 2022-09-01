package forum.board.controller;

import forum.board.controller.DTO.SignUpForm;
import forum.board.service.SignUpService;
import forum.board.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 회원가입 화면을 뿌려주는 컨트롤러
 * - 회원가입 화면
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/bgshop/signup")
public class SignUpController {

    private final SignUpService signUpService;
    private final SignupValidator signupValidator;

    @GetMapping
    public String signUpPage(@ModelAttribute("member") SignUpForm signUpForm)
    {
        return "signUp";
    }


    @PostMapping
    public String signupProcess(@Valid @ModelAttribute("member") SignUpForm signUpForm, BindingResult bindingResult)
    {

        if(signupValidator.supports(signUpForm.getClass())) {

            signupValidator.validate(signUpForm,bindingResult);

            if (bindingResult.hasErrors()) {
                return "signUp";
            }
        }

        // 검증 성공시 DB에 회원가입 데이터 적재
       signUpService.doSignUp(signUpForm);
        return "signupSuccess";
    }



}
