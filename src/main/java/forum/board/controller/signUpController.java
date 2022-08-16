package forum.board.controller;

import forum.board.controller.DTO.signUpForm;
import forum.board.domain.Member;
import forum.board.service.signUpService;
import forum.board.validation.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
public class signUpController {

    private final signUpService signUpService;
    private final SignupValidator signupValidator;

    @GetMapping
    public String signUpPage(@ModelAttribute("member") signUpForm signUpForm)
    {
        return "signUp";
    }


    @PostMapping
    public String signupProcess(@Valid @ModelAttribute("member") signUpForm signUpForm, BindingResult bindingResult)
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
