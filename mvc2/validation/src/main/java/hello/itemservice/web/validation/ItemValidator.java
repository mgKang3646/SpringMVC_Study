package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {
    @Override // 여러 개의 검증기가 들어올 때
    public boolean supports(Class<?> clazz) {

        // == 보다 isAssignableFrom 사용 ( 자식 클래스도 모두 가능 )
        return Item.class.isAssignableFrom(clazz); //Item을 검증할 수 있는 Validator임을 표시
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"itemName","required");

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price","range",new Object[]{1000,100000000},null);
        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            errors.rejectValue("quantity","max",new Object[]{9999},null);

        }

        // 틀정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if(resultPrice < 10000){
                errors.reject("totalPriceMin", new Object[]{10000,resultPrice},null);
            }
        }

    }
}
