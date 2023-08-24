package hello.login.web.session;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>(); // 여러 스레드가 동시에 Map 접근이 가능


    // 세션 생성
    // 클라이언트로부터 로그인 요청이 들어오면 세션을 생성하고
    // 세션ID를 쿠키로 클라이언트로 보낸다.
    public void createSession(Object value, HttpServletResponse response){
        // 세션 id를 생성하고 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME,sessionId);
        response.addCookie(mySessionCookie);

    }

    // 쿠키  조회
    // request에 담긴 세션id로 회원정보를 가져온다.
    public Object getSession(HttpServletRequest request){

        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie == null) return null;
        return sessionStore.get(sessionCookie.getValue());
    }
    
    // 세션 만료
    // 쿠키로 들어온 세션ID와 일치하는 정보를 삭제한다.
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest request,String cookieName){

        if(request.getCookies() == null){
            return null;
        }

       return Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(SESSION_COOKIE_NAME))
                .findFirst()
                .orElse(null);

    }



}
