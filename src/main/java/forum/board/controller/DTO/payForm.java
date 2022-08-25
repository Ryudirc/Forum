package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class payForm {

    private String pg;
    private String amount;
    private String merchantUid;
    private String buyerName;

    @Override
    public String toString() {
        return "payForm{" +
                "pg='" + pg + '\'' +
                ", amount='" + amount + '\'' +
                ", merchantUid='" + merchantUid + '\'' +
                ", buyerName='" + buyerName + '\'' +
                '}';
    }
}
