package forum.board.validation;

import forum.board.controller.DTO.ItemSaveForm;
import forum.board.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ItemSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ItemSaveForm itemSaveForm = (ItemSaveForm) target;


        if(itemSaveForm.getContent().equals("<p><br></p>"))
        {
            errors.rejectValue("content","duplicate");
        }


    }
}
