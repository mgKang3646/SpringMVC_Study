package hello.typeconverter.controller;


import hello.typeconverter.type.IpPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data");
        Integer intValue = Integer.valueOf(data);
        System.out.println("intValue = " + intValue);
        return "ok";
    }


    // @RequestParam은 문자열로 들어론 숫자를 자동으로 타입변환을 해주는 것
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam int data){
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/ip-port") // @ModelAttribute @PathVariable에서 모두 동작이 잘됨
    public String ipPort(@RequestParam IpPort ipPort){
        System.out.println("ipPort = " + ipPort.getIp());
        System.out.println("ipPort = " + ipPort.getPort());
        return "ok";
    }
}
