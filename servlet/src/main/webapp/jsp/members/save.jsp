<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member"%>
<%@ page import="hello.servlet.domain.member.MemberRepository"%>

<%
        MemberRepository memberRepository = MemberRepository.getInstance();
        //request, response는 import없어도 사용이 가능하다. ( 서블릿의 서비스 부분이라 생각하면 된다. )
        String username = request.getParameter("username");
        int age =Integer.parseInt(request.getParameter("age")) ;

        Member member=new Member(username,age);
        memberRepository.save(member);

%>
<html>
    <head>
       <title>Title</title>
    </head>
    <body>
    <ul>
        <li>id=<%=member.getId()%></li>
        <li>username=<%=member.getUsername()%></li>
        <li>age=<%=member.getAge()%></li>
    </ul>
    <a href="/index.html">메인</a>
     </body>
</html>
