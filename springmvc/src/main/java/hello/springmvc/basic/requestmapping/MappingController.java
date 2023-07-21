package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController // 오류 발생시, JSON 방식으로 반환
public class MappingController {

    @RequestMapping(value = {"/hello-basic","/hello-go"},method = RequestMethod.GET)
    public String mappingGetV1(){
        log.info("helloBasic");
        return "OK";
    }

    @GetMapping({"/mapping-get-v2","/hello-go"})
    public String mappingGetV2(){
        log.info("helloBasic");
        return "OK";
    }


    @GetMapping("/mapping1/{userId}")
    public String mappingPath1(@PathVariable("userId") String data){
        log.info("MappingPath userId ={}",data);
        return "OK";
    }

    @GetMapping("/mapping2/{userId}")
    public String mappingPath2(@PathVariable String userId){ // 변수명이 같으면 축약하여 쓸 수 있다.
        log.info("MappingPath userId ={}",userId);
        return "OK";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}") // 다중 파라미터
    public String mappingPath3(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId = {} . orderId = {}", userId,orderId);
        return "OK";
    }

    @GetMapping(value = "/mapping-param1", params = "mode=debug") // ?뒤에 특정 파라미터 조건이 있어야 호출가능 ex) /mapping-param?mode=debug
    public String mappingParam1(){
        log.info("mappingParam");
        return "OK";
    }

    @GetMapping(value = "/mapping-param2", headers = "mode=debug") // 헤더에 특정 조건이 있어야 호출가능
    public String mappingParam2(){
        log.info("mappingParam");
        return "OK";
    }

    @GetMapping(value = "/mapping-param3", consumes = "application/json") // Content type 기반 헤더, application/json
    public String mappingParam3(){
        log.info("mappingParam");
        return "OK";
    }

    @GetMapping(value = "/mapping-param4", consumes = MediaType.APPLICATION_JSON_VALUE) // Content type 기반 헤더, application/json
    public String mappingParam4(){
        log.info("mappingParam");
        return "OK";
    }

    @GetMapping(value = "/mapping-param5", produces = "text/html") // Accept 헤더기반 Media Type
    public String mappingParam5(){
        log.info("mappingParam");
        return "OK";
    }

    @GetMapping(value = "/mapping-param6", produces = MediaType.TEXT_HTML_VALUE) // Accept 헤더기반 Media Type
    public String mappingParam6(){
        log.info("mappingParam");
        return "OK";
    }

}
