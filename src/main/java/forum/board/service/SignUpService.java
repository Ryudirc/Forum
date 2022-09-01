package forum.board.service;

import forum.board.controller.DTO.SignUpForm;
import forum.board.domain.Member;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MybatisMemberRepository memberRepository;

    public Member doSignUp(SignUpForm form)
    {
        // 회원가입 시 폼으로 입력되어 넘어오는 데이터를 통해 validation 을 하고 멤버리포지토리 통해 DB에 저장

        Member member = new Member(form.getMemberAcct(), form.getMemberPw(), form.getMemberName(),form.getMemberEmail());
        memberRepository.save(member);
        return member;
    }
}
