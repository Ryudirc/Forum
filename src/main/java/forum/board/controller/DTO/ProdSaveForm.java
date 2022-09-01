package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter @Setter
public class ProdSaveForm {

    private String prodCategory;
    @NotBlank(message = "상품명을 입력하여 주십시오.")
    private String prodName;
    @NotNull(message = "재고수량을 입력하여 주십시오.")
    private Integer prodStock;
    @NotNull(message = "상품가격을 입력하여 주십시오.")
    private Integer prodPrice;
    @NotNull(message = "할인상품가격을 입력하여 주십시오.")
    private Integer prodDiscountPrice;
    private MultipartFile prodImg;


}
