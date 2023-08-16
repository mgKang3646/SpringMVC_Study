package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [ 비즈니스 로직 ]
        String username = request.getParameter("username");
        int age =Integer.parseInt(request.getParameter("age")) ;

        Member member =new Member(username,age);
        memberRepository.save(member);

        // [ 결과 반환하기 ]
        request.setAttribute("member",member); //request 내부저장소에 처리 결과 데이터 저장하기
        return new MyView("/WEB-INF/views/save-result.jsp"); // VIEW 객체 반환
    }

}
