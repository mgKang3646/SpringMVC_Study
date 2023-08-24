package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver  {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try{
            //ExceptionResolver 를 구현하기가 힘듦
            if(ex instanceof UserException){
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                if("application/json".equals(acceptHeader)){
                    Map<String,Object> errorResult = new HashMap<>();
                    errorResult.put("ex",ex.getClass());
                    errorResult.put("message",ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult);

                    // Servlet 컨테이너까지 예외를 보내지 않고 정상 종료 시키기
                    // ModelAndView를 반환하니깐 직접 HTTP BODY 영역에 데이터를 write 해야 한다.
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView();

                }else{
                    return new ModelAndView("error/500");
                }

            }

        } catch (IOException e){
            log.error("resolver ex",ex);
        }
        return null;
    }

}
