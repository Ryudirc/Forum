package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class orderList {

    private Long listId;
    private Long orderId;
    private String prodName;
    private int prodCnt;
    private int prodPrice;

    public orderList(Long orderId, String prodName, int prodCnt, int prodPrice) {
        this.orderId = orderId;
        this.prodName = prodName;
        this.prodCnt = prodCnt;
        this.prodPrice = prodPrice;
    }
}
