package forum.board.interceptor;

import forum.board.global.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static forum.board.interceptor.LogInterceptor.REQUEST_LOG_ID;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uuid = (String)request.getAttribute(REQUEST_LOG_ID);
        String requestURI = request.getRequestURI();
        log.info("(로그인체크 인터셉터 동작 시작)[요청자 : {}][URI : {}]",uuid,requestURI);

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("(로그인체크 인터셉터 : 미인증 사용자 요청)[요청자 : {}][URI : {}]",uuid,requestURI);

            //로그인 화면으로 redirect
            response.sendRedirect("/bgshop/login?redirectURL=" + requestURI);

            return false;
        }

        log.info("(로그인체크 인터셉터 : 정상 사용자 요청)[요청자 : {}][URI : {}]",uuid,requestURI);

        return true;
    }

}
