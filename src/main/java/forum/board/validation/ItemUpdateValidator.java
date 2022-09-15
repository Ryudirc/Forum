package forum.board.validation;

import forum.board.controller.DTO.ItemSaveForm;
import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemUpdateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ItemUpdateForm itemUpdateForm = (ItemUpdateForm) target;

        if(itemUpdateForm.getContent().equals("<p><br></p>"))
        {
            errors.rejectValue("content","duplicate");
        }

    }
}
