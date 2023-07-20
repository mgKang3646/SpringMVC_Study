package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/*  커맨트 + B로 추적
* 	@Override
	protected boolean isHandler(Class<?> beanType) {
		return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
				AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
	}
	*
	* 어노테이션이 Controller이거나 RequestMapping이 클래스 타입으로 되어 있으면 Handler로 인식하여 True를 반환한다.

*
* */
@Controller
//@Component // Bean으로 등록
//@RequestMapping // 핸들러임을 명시
public class SpringMemberFromControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form");
    }
}
