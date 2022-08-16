package forum.board.service;

import forum.board.controller.DTO.loginForm;
import forum.board.domain.Member;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MybatisMemberRepository memberRepository;


    public Member doLogin(loginForm form)
    {
        if(memberRepository.findByLoginInfo(form.getMemberAcct(),form.getMemberPw()) == null)
        {
            return null;
        }
        return memberRepository.findByLoginInfo(form.getMemberAcct(),form.getMemberPw());
    }


}
