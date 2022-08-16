package forum.board.validation;

import forum.board.controller.DTO.signUpForm;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignupValidator implements Validator {

    private final MybatisMemberRepository memberRepository;



    @Override
    public boolean supports(Class<?> clazz) {
        return signUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        signUpForm signUpForm = (signUpForm) target;

        //아이디 중복검증
        if(memberRepository.findByLoginId(signUpForm.getMemberAcct()) != null)
        {
            if (!(memberRepository.findByLoginId(signUpForm.getMemberAcct()).isBlank()))
            {
                errors.rejectValue("memberAcct", "duplicate");
            }
        }


        // 비밀번호,비밀번호 확인 검증
        if(!signUpForm.getMemberPwConfirm().isBlank()) {
            if (!signUpForm.getMemberPw().equals(signUpForm.getMemberPwConfirm())) {
                errors.rejectValue("memberPwConfirm", "confirm");
            }
        }

        //닉네임 중복 검증
        if(memberRepository.findByName(signUpForm.getMemberName()) != null)
        {
            if(!(memberRepository.findByName(signUpForm.getMemberName()).isBlank()))
            {
                errors.rejectValue("memberName","duplicate");
            }
        }

        //이메일 중복 검증
        if(memberRepository.findByEmail(signUpForm.getMemberEmail()) != null)
        {
            if(!(memberRepository.findByEmail(signUpForm.getMemberEmail()).isBlank()))
            {
                errors.rejectValue("memberEmail","duplicate");
            }
        }



    }
}
