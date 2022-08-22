package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class orderProd {

    private String prodName;
    private int prodCnt;
    private int prodPrice;

    private int calcPrice;


    @Override
    public String toString() {
        return "orderProd{" +
                "prodName='" + prodName + '\'' +
                ", prodCnt=" + prodCnt +
                ", prodPrice=" + prodPrice +
                ", calcPrice=" + calcPrice +
                '}';
    }
}
