package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class orderHistory {

    private Long orderId;
    private String memberRealName;
    private String memberPhone;
    private String orderStatus;
    private List<orderProd> orderProdList;
    private int totalPrice;
    private int modalSequence;

    @Override
    public String toString() {
        return "orderHistory{" +
                "orderId=" + orderId +
                ", memberRealName='" + memberRealName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderProdList=" + orderProdList +
                ", totalPrice=" + totalPrice +
                ", modalSequence=" + modalSequence +
                '}';
    }
}
