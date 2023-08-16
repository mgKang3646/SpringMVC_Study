package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller // 파라미터로 데이터가 넘어오는 경우
public class RequestParamController {
    @RequestMapping("/request-param-v1") // HttpServlet 사용하기
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age={}",username,age);

        response.getWriter().write("OK");
    }

    @ResponseBody // return값을 responseBody에 넣는다.  @RestContorller와 같은 기능
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge){
        log.info("username = {}, age={}", memberName,memberAge  );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3") // 이름이 동일하면 단순화 가능
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age){
        log.info("username = {}, age={}", username,age  );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4") // 이름이 동일하면 @RequestParam 생략가능, 명확하게 명시하기 위해 @RequestParam을 사용하는것도 좋음
    public String requestParamV4(String username,int age){
        log.info("username = {}, age={}", username,age  );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-required") // 이름이 동일하면 @RequestParam 생략가능, 명확하게 명시하기 위해 @RequestParam을 사용하는것도 좋음
    public String requestParamRequired(
            @RequestParam(required = true) String username, // username이 필수값인 경우 ( 디폴트여서 굳이 넣을필요없음을 검정생 으로 표현 ) 주의! 빈문자 ( "" ) 는 통과! ?username=
            @RequestParam(required = false) Integer age){ // age가 필수값이 아닌 경우, NULL값이 들어갈 수 있도록 int가 아닌 Integer를 사용한다.
        log.info("username = {}, age={}", username,age  );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true,defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") Integer age){
        log.info("username = {}, age={}", username,age  );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String,Object> paramMap){
        log.info("username = {}, age={}", paramMap.get("username"),paramMap.get("age") );
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-attribute-v0")
    public String modelAttributeV0(@RequestParam String username,@RequestParam int age){

        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        // [ 비즈니스 로직... ]

        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-attribute-v1")
    public String modelAttributeV1(@ModelAttribute  HelloData helloData){ // setter 함수로 데이터를 바인딩하여 객체를 생성한다.
        log.info("helloData = {}",helloData);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-attribute-v2")
    public String modelAttributeV2(HelloData helloData){ // @ModelAttibute 생략가능
        log.info("helloData = {}",helloData);
        return "OK";
    }





}
