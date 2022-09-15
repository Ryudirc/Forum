package forum.board.interceptor;

import forum.board.domain.LoginMember;
import forum.board.global.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static forum.board.interceptor.LogInterceptor.REQUEST_LOG_ID;

@Slf4j
public class AuthCheckInterceptor implements HandlerInterceptor {

    //권한 관리 인터셉터
    // 사용자의 Role 이 USER 이거나, 아무것도 없는 경우(whiteSpace,null,"") 어드민페이지로의 접근을 제한한다.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(REQUEST_LOG_ID);
        log.info("(권한체크 인터셉터 동작)[요청자 : {}][URI : {}]",uuid,requestURI);

        HttpSession session = request.getSession();
        LoginMember Member = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(!(Member.getRole().equals("ADMIN"))) {
            log.info("(권한체크 인터셉터 : 어드민이 아닌 요청자)[요청자 : {}][URI : {}]",uuid,requestURI);
            response.sendRedirect("/");

            return false;
        }

        return true;
    }
}
