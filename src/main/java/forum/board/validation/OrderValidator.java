package forum.board.validation;

import forum.board.controller.DTO.OrderSaveForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderSaveForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        OrderSaveForm form = (OrderSaveForm) target;

        //주소 입력 검증
        if(form.getAddress().isBlank() || form.getDetailAddress().isBlank() || form.getExtraAddress().isBlank()){
            errors.reject("requiredAddress");
        }

        //포인트 검증
        if((form.getOwnPoint() - form.getTotalPoint()) < 0) {
            errors.rejectValue("ownPoint","insufficientCash");
        }

    }
}
