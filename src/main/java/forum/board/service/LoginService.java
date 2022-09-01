package forum.board.service;

import forum.board.controller.DTO.LoginForm;
import forum.board.domain.Member;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MybatisMemberRepository memberRepository;


    public Member doLogin(LoginForm form)
    {
        if(memberRepository.findByLoginInfo(form.getMemberAcct(),form.getMemberPw()) == null)
        {
            return null;
        }
        return memberRepository.findByLoginInfo(form.getMemberAcct(),form.getMemberPw());
    }


}
