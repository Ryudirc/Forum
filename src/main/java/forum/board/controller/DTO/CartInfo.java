package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartInfo {

    private Long memberId;
    private Long prodId;
    private String prodName;
    private int prodPrice;
    private int prodCnt;
    private int stock;

    public CartInfo() {}
    public CartInfo(Long memberId, Long prodId, String prodName, int prodPrice, int prodCnt, int stock) {
        this.memberId = memberId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodCnt = prodCnt;
        this.stock = stock;
    }
}
