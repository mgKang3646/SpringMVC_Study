package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("message = {}",messageBody);

        response.getWriter().write("OK");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer reponseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("message = {}",messageBody);
        reponseWriter.write("OK");
    }

    @PostMapping("/request-body-string-v3") // HttpEntity 사용
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody(); // header와 body 데이터를 편리하게 사용가능
        log.info("message = {}",messageBody);
        return new HttpEntity<>("ok"); //응답에도 사용 가능
    }

    @PostMapping("/request-body-string-v3-1")
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody(); // header와 body 데이터를 편리하게 사용가능
        log.info("message = {}",messageBody);
        return new ResponseEntity<String>("OK",HttpStatus.CREATED); //응답에도 사용 가능
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4") // HttpEntity의 RequestBody를 읽어 바로 파라미터로 넘겨준다.
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("message = {}",messageBody);
        return "OK";
    }
}
