package forum.board.global;

import forum.board.controller.DTO.MemberUpdateForm;
import forum.board.domain.LoginMember;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    public void changeSessionMemberName(HttpServletRequest request, MemberUpdateForm memberUpdateForm)
    {
        HttpSession session = request.getSession();
        LoginMember member = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        member.setMemberName(memberUpdateForm.getMemberName());

        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        session.setAttribute(SessionConst.LOGIN_MEMBER,member);

    }


}
