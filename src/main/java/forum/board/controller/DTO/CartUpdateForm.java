package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartUpdateForm {

    private Long prodId;
    private int prodCnt;

}
