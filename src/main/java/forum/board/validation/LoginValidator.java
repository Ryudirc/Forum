package forum.board.validation;

import forum.board.controller.DTO.LoginForm;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator{

    private final MybatisMemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
        // loginForm == clazz 를 확인
        // loginForm 의 자식 class 까지 커버됨
    }

    @Override
    public void validate(Object target, Errors errors) {

        LoginForm loginForm = (LoginForm) target;

        if(loginForm.getMemberAcct().isEmpty()|| loginForm.getMemberPw().isEmpty())
        {
            errors.reject("formEmpty");
            return;
        }

        if(memberRepository.findByLoginInfo(loginForm.getMemberAcct(), loginForm.getMemberPw()) == null)
        {
            errors.reject("loginFail");
        }


    }
}
