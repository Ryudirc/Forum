package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;


@Getter @Setter
public class ItemUpdateForm {

    private Long itemId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public ItemUpdateForm(Long itemId,String title, String content) {
        this.itemId = itemId;
        this.title = title;
        this.content = content;
    }
}
