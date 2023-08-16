package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> requestModel,Map<String,Object> responseModel) {

        // [ 비즈니스 로직 ]
        String username = requestModel.get("username"); // 요청 데이터 가져오기
        int age = Integer.parseInt(requestModel.get("age"));

        Member member = new Member(username,age);
        memberRepository.save(member);

        // [ 응답데이터 설정 ]
        responseModel.put("member",member); // 응답 데이터 설정
        return "save-result"; // View 리졸버에 전달할 View name 반환

    }
}

