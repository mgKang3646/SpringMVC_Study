package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//JSON 응답데이터 보내기
@WebServlet(name = "responseJsonServlet", urlPatterns="/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //[ Header 영역 데이터 설정 ]
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 객체에 데이터 세팅하기
        HelloData helloData = new HelloData();
        helloData.setUsername("KIM");
        helloData.setAge(20);

        //[ Body 영역 데이터 설정 ]
        String result = objectMapper.writeValueAsString(helloData); // JSON 형식의 문자열로 변환하기
        response.getWriter().write(result);
    }
}
