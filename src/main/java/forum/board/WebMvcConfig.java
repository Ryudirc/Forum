package forum.board;

import forum.board.interceptor.AuthCheckInterceptor;
import forum.board.interceptor.LogInterceptor;
import forum.board.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/freeboard/**","/login/**","/signup/**","/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/forum/freeBoard/**","/profile/**")
                .excludePathPatterns("/freeboard/**","/login/**","/signup/**","/error","/","/bgshop/login","/bgshop/signup");
            //excludePathPatterns 에 추가해야하는 URI 는 "상품상세" 페이지 정도.

        registry.addInterceptor(new AuthCheckInterceptor())
                .order(3)
                .addPathPatterns("/profile/admin/**")
                .excludePathPatterns("/freeboard/**","/login/**","/signup/**","/error","/bgshop/login","/bgshop/signup","/profile/myPage/**");
    }
}
