package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/method")
    public String headerMethod(HttpMethod httpMethod){ // 메소드 정보 받기
        log.info("httpMethod={}",httpMethod);
        return "OK";
    }
    @RequestMapping("/locale")
    public String headerLocale(Locale locale){ // Locale 정보 받기
        log.info("locale={}",locale);
        return "OK";
    }
    @RequestMapping("/headermap")
    public String headerMap( @RequestHeader MultiValueMap<String,String> headerMap){ // 모든 HTTP 헤더데이터를 MultiValueMap 형식으로 받기
        log.info("headerMap={}",headerMap);
        return "OK";
    }
    @RequestMapping("/host")
    public String headerHost(@RequestHeader("host") String host){ // 호스트 정보 받기
        log.info("header host={}",host);
        return "OK";
    }
    @RequestMapping("/cookie")
    public String headerCookie(@CookieValue(value = "myCookie", required = false) String cookie){ // 쿠키정보 받기
        log.info("myCookie={}",cookie);
        return "OK";
    }
}
