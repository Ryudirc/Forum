package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class orderStock {

    private int prodCnt;
    private Long prodId;

    public orderStock(int prodCnt, Long prodId) {
        this.prodCnt = prodCnt;
        this.prodId = prodId;
    }

    @Override
    public String toString() {
        return "orderStock{" +
                "prodCnt=" + prodCnt +
                ", prodId=" + prodId +
                '}';
    }
}
