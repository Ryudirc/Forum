package forum.board.service;

import forum.board.domain.Member;
import forum.board.repository.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MybatisMemberRepository memberRepository;


    public void changeMemberPoints(Long memberId,HttpServletRequest request)
    {
        Member member = memberRepository.findById(memberId);
        int changePoints = Integer.parseInt(request.getParameter("changePoints"));
        member.setPoints(changePoints);

        memberRepository.updatePoints(memberId,member);
    }

    public void addMemberPoints(Long memberId,HttpServletRequest request)
    {
        Member member = memberRepository.findById(memberId);
        int addPoints = Integer.parseInt(request.getParameter("addPoints"));
        int ownPoint = member.getPoints();
        int resultPoints = ownPoint + addPoints;
        member.setPoints(resultPoints);

        memberRepository.updatePoints(memberId,member);

    }

}
