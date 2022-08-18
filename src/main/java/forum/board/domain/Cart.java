package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cart {

    private Long cartId;
    private Long memberId;
    private Long prodId;
    private String prodName;
    private int prodPrice;
    private int prodCnt;

    public Cart() {}

    public Cart(Long memberId, Long prodId, String prodName, int prodPrice, int prodCnt) {
        this.memberId = memberId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodCnt = prodCnt;
    }
}
