package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseTextServlet", urlPatterns = "/response-text")
public class ResponseTextServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //[ Header 영역 데이터 설정 ]
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        //[Body 영역 데이터 설정 ]
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요");
    }
}
