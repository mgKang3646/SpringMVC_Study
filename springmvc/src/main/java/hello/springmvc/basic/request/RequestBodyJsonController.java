package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody,HelloData.class);
        log.info("username= {}, age={}", helloData.getUsername(),helloData.getAge());

        response.getWriter().write("OK");
    }


    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody,HelloData.class);
        log.info("username= {}, age={}", helloData.getUsername(),helloData.getAge());

        return "ok"; // @RestController가 아니므로 @ResponseBody로 선언해주어야 한다.
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException { // @RequestBody 생략X , 생략하면 @ModelAttribute가 자동으로 선언된다. 요청 파라미터 처리방식이므로 적용X
        /*
            HelloData helloData = objectMapper.readValue(messageBody,HelloData.class);
            //HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
            //messageBody를 HelloData로 변환 ( 컨버터 )
        */
        log.info("username= {}, age={}", helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {

        HelloData helloData = httpEntity.getBody();
        log.info("username= {}, age={}", helloData.getUsername(),helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {

        log.info("username= {}, age={}", data.getUsername(),data.getAge());
        return data; // HelloData를 return하면 컨버너가 Json으로 변환하여 반환
    }


}
