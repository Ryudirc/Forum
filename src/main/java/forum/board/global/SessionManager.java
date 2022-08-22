package forum.board.global;

import forum.board.controller.DTO.memberUpdateForm;
import forum.board.domain.loginMember;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    public void changeSessionMemberName(HttpServletRequest request, memberUpdateForm memberUpdateForm)
    {
        HttpSession session = request.getSession();
        loginMember member = (loginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
        member.setMemberName(memberUpdateForm.getMemberName());

        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        session.setAttribute(SessionConst.LOGIN_MEMBER,member);

    }


}
