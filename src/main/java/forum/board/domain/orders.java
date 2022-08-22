package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class orders {

    private Long orderId;

    private Long memberId;
    private String memberRealName;
    private String memberPhone;
    private String deliveryRequirements;
    private String memberAddress;

    private String orderStatus;

    public orders() {}

    public orders(Long memberId, String memberRealName, String memberPhone, String deliveryRequirements, String memberAddress) {
        this.memberId = memberId;
        this.memberRealName = memberRealName;
        this.memberPhone = memberPhone;
        this.deliveryRequirements = deliveryRequirements;
        this.memberAddress = memberAddress;
    }

    @Override
    public String toString() {
        return "orders{" +
                "orderId=" + orderId +
                ", memberId=" + memberId +
                ", memberRealName='" + memberRealName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", deliveryRequirements='" + deliveryRequirements + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                '}';
    }
}
