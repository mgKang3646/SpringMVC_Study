package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder // Controller가 호출될 때마다 WebDataBinder가 새로 생성한 다음 Validator를 넣어놓는다.
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    @PostMapping("/add") // BindingResult는 @ModelAttribute 뒤에 와야함
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        // 상품명이 공백인 경우
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName","상품이름은 필수 입니다."));
        }

        // 가격이 범위 밖인 경우
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다. "));
        }

        // 수량이 범위 밖인 경우
        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999까지 혀용합니다. "));
        }

        // 두 개 항목 이상의 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add") // BindingResult는 검증해야 할 @ModelAttribute 바로 뒤에 와야함
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());

        // 검증로직
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName","required.default.message"},null,null));

        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,1000000},null));
        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999},null));


        }

        // 틀정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));

            }
        }

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증 성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add") // BindingResult는 검증해야 할 @ModelAttribute 바로 뒤에 와야함
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        /*
            // 바인딩 실패하면 바로 에러 코드를 내도록 할 수 있다.
            if(bindingResult.hasErrors()){
                log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
                return "validation/v2/addForm";
            }
        */


        log.info("objectName={}",bindingResult.getObjectName());
        log.info("target={}",bindingResult.getTarget());


        // 검증로직
//        if(!StringUtils.hasText(item.getItemName())){
//            bindingResult.rejectValue("itemName","required");
//        }

        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"itemName","required"); // 공백같은 단순한 기능만 제공

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.rejectValue("price","range",new Object[]{1000,100000000},null);
        }

        if(item.getQuantity() == null || item.getQuantity() >= 9999){
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);

        }

        // 틀정 필드가 아닌 복합 룰 검증
        if(item.getPrice() != null && item.getQuantity() != null){
            int resultPrice = item.getPrice()*item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice},null);
            }
        }

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증 성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add") // BindingResult는 검증해야 할 @ModelAttribute 바로 뒤에 와야함
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(item,bindingResult);

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증 성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add") // @Validated 검증기를 실행하라는 어노테이션
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // 검증에 실패하면 다시 입력폼으로 이동
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult); // model에 안 담아도 됨. 자동으로 넘어감
            return "validation/v2/addForm";
        }

        // 검증 성공 로직

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

