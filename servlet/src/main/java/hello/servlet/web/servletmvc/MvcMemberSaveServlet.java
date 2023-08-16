package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberSaveServlet",urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // [ 요청 데이터 가져오기 ]
        String username = request.getParameter("username");
        int age =Integer.parseInt(request.getParameter("age")) ;

        // [ 비즈니스 로직 ]
        Member member =new Member(username,age);
        memberRepository.save(member);

        // [ 응답 데이터 JSP로 보내기 ]
        request.setAttribute("member",member); // 전달할 데이터를 request의 내부저장소에 보관

        String viewPath = "/WEB-INF/views/save-result.jsp"; // JSP 페이지 경로
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath); // RequestDispatcher 객체 호출
        requestDispatcher.forward(request,response); // JSP로 request, response 데이터 전달
    }
}
