package forum.board;

import forum.board.controller.DTO.prodSaveForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class BeanValidationTest {


    @Test
    void byteSizeTest()
    {
        byte[] content = {};
        assertThat(content).hasSize(0);
    }

    @Test
    void beanValidation()
    {
        // given
        ValidatorFactory fac = Validation.buildDefaultValidatorFactory();
        Validator validator = fac.getValidator();

        prodSaveForm form = new prodSaveForm();
        form.setProdCategory("normalProd");
        form.setProdName("");
        form.setProdStock(null);
        form.setProdPrice(null);
        form.setProdDiscountPrice(null);

        byte[] content = {};
        MultipartFile multipartFile = new MockMultipartFile("MockFile",content);
        form.setProdImg(multipartFile);

        //when
        Set<ConstraintViolation<prodSaveForm>> violations = validator.validate(form);
        for (ConstraintViolation<prodSaveForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }

        //then



    }
}
