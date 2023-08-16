package hello.thymeleafbasic.basic;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data","Hello Data");
        return "basic/text-basic";
    }

    @GetMapping("text-unescape")
    public String textUnescape(Model model){
        model.addAttribute("data","Hello <b>Hello Data</b>");
        return "basic/text-unescape";
    }

    @GetMapping("/variable")
    public String variable(Model model){

        User userA = new User("UserA", 10);
        User userB = new User("UserB", 10);

        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String,User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);

        model.addAttribute("user",userA );
        model.addAttribute("users",list);
        model.addAttribute("userMap",map);

        return "basic/variable";
    }

   /*
   * 스프링부트 2.x 기준
   @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session){
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }

    */

    // 스프링 부트 3.x 기준, 더이상 request,response,servletContext 지원X
    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }

    @GetMapping("/date")
    public String date(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }

    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1","date1");
        model.addAttribute("param2","date2");
        return "basic/link";
    }


    @Component("helloBean")
    static class HelloBean{
        public String hello(String data){
            return "Hello" + data;
        }
    }

    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data","Srping!");
        return "basic/literal";
    }

    @GetMapping("/operation")
    public String operation(Model model){
        model.addAttribute("nullData",null);
        model.addAttribute("data","Spring!");
        return "basic/operation";
    }



    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}