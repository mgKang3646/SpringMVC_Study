package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range; // 하이버네이트 구현체만 제공하는 검증기

// JAVA 표준 인터페이스
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang="javascript", script="_this.price * _this.quantity >= 10000 ", message = " 총합이 10000원 넘게 입력해주세요")
public class Item {

    //@NotNull(groups = UpdateCheck.class)
    private Long id;

    //@NotBlank(groups = {SaveCheck.class,UpdateCheck.class})
    private String itemName;

    //@NotNull(groups = {SaveCheck.class,UpdateCheck.class})
    //@Range(min=1000,max=1000000,groups = {SaveCheck.class,UpdateCheck.class})
    private Integer price;

   // @NotNull(groups = {SaveCheck.class,UpdateCheck.class})
    //@Max(value=9999,groups = SaveCheck.class)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
