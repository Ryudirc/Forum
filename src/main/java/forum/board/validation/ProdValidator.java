package forum.board.validation;

import forum.board.controller.DTO.prodSaveForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProdValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return prodSaveForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        prodSaveForm form = (prodSaveForm) target;


        //카테고리&상품정보 일치여부 검증
        if(form.getProdCategory().equals("normalProd") && form.getProdDiscountPrice() != null)
        {
            if(form.getProdDiscountPrice() != 0) {
                errors.rejectValue("prodDiscountPrice", "mismatchCategory");
                }
        }

        if(form.getProdCategory().equals("discountProd") && form.getProdDiscountPrice() != null && form.getProdPrice() != null)
        {
            if(form.getProdDiscountPrice() == 0 || form.getProdDiscountPrice() < 0)
            {
                errors.rejectValue("prodDiscountPrice","invalidInput");
            }
            if(form.getProdDiscountPrice().compareTo(form.getProdPrice()) == 0|| form.getProdDiscountPrice() > form.getProdPrice())
            {
                errors.rejectValue("prodDiscountPrice","illogicalInput");
            }
        }

        //이미지 파일 업로드 여부 check
        if(form.getProdImg().getSize() == 0)
        {
            errors.rejectValue("prodImg","EmptyFile");
        }

    }
}
