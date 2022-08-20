package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class orderSaveForm {

    private String memberRealName;
    private String memberPhone;
    private String deliveryRequirements;
    private String address;
    private String detailAddress;
    private String extraAddress;


    @Override
    public String toString() {
        return "orderSaveForm{" +
                "memberRealName='" + memberRealName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", deliveryRequirements='" + deliveryRequirements + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", extraAddress='" + extraAddress + '\'' +
                '}';
    }
}
