package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class orderSaveForm {

    @NotBlank
    private String memberRealName;
    @NotBlank @Pattern(regexp = "^01(?:0)-(?:\\d{3}|\\d{4})-(\\d{4})$")
    private String memberPhone;
    @NotBlank
    private String deliveryRequirements;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private int totalPoint;
    private int OwnPoint;

    @Override
    public String toString() {
        return "orderSaveForm{" +
                "memberRealName='" + memberRealName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", deliveryRequirements='" + deliveryRequirements + '\'' +
                ", address='" + address + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", extraAddress='" + extraAddress + '\'' +
                ", totalPoint=" + totalPoint +
                ", OwnPoint=" + OwnPoint +
                '}';
    }
}
