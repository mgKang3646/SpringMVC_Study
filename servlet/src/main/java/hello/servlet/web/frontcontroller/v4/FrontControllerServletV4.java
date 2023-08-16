package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4",urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form",new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [ STEP1. 요청된 Controller 탐색하기 ]
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        // 요청된 컨트롤러가 없을 시, 404 에러 발생
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // [ STEP2. 요청 데이터, 응답 데이터 생성하기  ]
        Map<String,String> requestModel = createParamMap(request,controller); // 요청 데이터용 Map 데이터
        Map<String,Object> responseModel = new HashMap<>(); // 응답 데이터용 Map 데이터

        // [ STEP3. 비즈니스 로직 처리후 결과 반환하기  ]
        String viewName = controller.process(requestModel, responseModel); // request와 response 대신에 requestModel, responseModel이 Controller로 전달

        // [ STEP4. VIEW 영역에 데이터 전달하기  ]
        MyView view = viewResolver(viewName); // JSP 경로 생성
        view.render(responseModel,request,response); // VIEW 영역에 데이터 전달
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp"); // 정해진 위치에 모여있는 JSP 파일
    }

    // Request에서 요청 데이터 추출하기
    private Map<String,String> createParamMap(HttpServletRequest request, ControllerV4 controller) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName-> paramMap.put(paramName, request.getParameter(paramName))); // 요청으로 들어온 데이터 모음
        return paramMap;
    }
}
