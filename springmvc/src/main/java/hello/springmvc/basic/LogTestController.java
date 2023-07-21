package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController // 반환값으로 Http 메시지 BODY에 넘길 데이터를 반환하는 것, REST 방식
//@Controller // 반환값으로 뷰이름을 넘기는 것
public class LogTestController {

    @RequestMapping("log-test")
    public String logTest(){
        String name= "Spring";

        System.out.println("name = " + name); // 시스템 콘솔에 남기는 것
        log.info("info log ={} ",name); // 파라미터 전달방식, 로그레벨에 따라 실행이 안됨
        log.info("info log ={} " + name); // 문자열 합치기 방식, 로그레벨에 상관없이 문자열 합치기 연산 실행 ( CPU 소모 ), 추천X
        //로그를 파일에 남길 수 있도록 설정이 가능하여 여러 설정이 가능하다.
        //성능도 System.out보다 훨씬 좋다.

        return "OK";
    }
}
