package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CartSaveForm {

    private String prodName;
    private int prodPrice;
    private int prodCnt;

    @Override
    public String toString() {
        return "CartSaveForm{" +
                "prodName='" + prodName + '\'' +
                ", prodPrice=" + prodPrice +
                ", prodCnt=" + prodCnt +
                '}';
    }
}
