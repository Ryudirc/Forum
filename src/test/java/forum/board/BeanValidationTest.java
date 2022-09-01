package forum.board;

import forum.board.controller.DTO.ProdSaveForm;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
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

        ProdSaveForm form = new ProdSaveForm();
        form.setProdCategory("normalProd");
        form.setProdName("");
        form.setProdStock(null);
        form.setProdPrice(null);
        form.setProdDiscountPrice(null);

        byte[] content = {};
        MultipartFile multipartFile = new MockMultipartFile("MockFile",content);
        form.setProdImg(multipartFile);

        //when
        Set<ConstraintViolation<ProdSaveForm>> violations = validator.validate(form);
        for (ConstraintViolation<ProdSaveForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }

        //then

    }

    @Test
    void IfTest()
    {
        int num=1;
        int num2=1;
        int result = 0;

        if(num <= num2)
        {
            result = 10;
        }

        System.out.println("result = " + result);

    }


    @Test
    void variableTest()
    {
        List<Integer> integerList = new ArrayList<>();

        Integer numA = 10;
        Integer numB = 20;
        Integer numC = 30;

        integerList.add(numA);
        integerList.add(numB);
        integerList.add(numC);

        Integer sum = 0;
        for (Integer integer : integerList) {
            sum += integer.intValue();
        }

        System.out.println("sum = " + sum);

    }
}
