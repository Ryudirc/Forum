package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Products {

    private Long prodId;
    private String prodCategory;
    private String prodName;
    private int prodStock;
    private int prodPrice;
    private int prodDiscountPrice;
    private UploadProdFile prodImg;

}
