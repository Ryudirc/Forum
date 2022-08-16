package forum.board.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String REQUEST_LOG_ID = "uuid";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_LOG_ID,uuid);
        log.info("(preHandle)[요청자 : {}][URI : {}]",uuid,requestURI);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = (String)request.getAttribute(REQUEST_LOG_ID);
        String requestURI = request.getRequestURI();

        log.info("(postHandle)[요청자 : {}][URI : {}][모델앤뷰 : {}]",uuid,requestURI,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String uuid = (String) request.getAttribute(REQUEST_LOG_ID);
        String responseURI = request.getRequestURI();

        if(ex != null)
        {
            log.info("(afterCompletion)[요청자 : {}][Response : {}]",uuid,responseURI);
            log.error("error handle by LogInterceptor ",ex);
        }

        log.info("(afterCompletion)[요청자 : {}][응답정상]",uuid);
    }
}
