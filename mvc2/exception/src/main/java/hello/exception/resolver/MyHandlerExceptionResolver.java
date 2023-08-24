package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if(ex instanceof IllegalArgumentException){
            log.info("IllegalArgumentException resolver to 400");
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage()); // 500 -> 400으로 상태코드 바꾸기
                return new ModelAndView(); // 예외가 발생했지만 정상적으로 return 하여 삼켜버리기
            } catch (IOException e) {
                log.error("resolver ex : " ,e);
            }
        }

        return null;
    }
}
