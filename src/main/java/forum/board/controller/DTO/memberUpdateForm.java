package forum.board.controller.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class memberUpdateForm {
    @NotBlank
    private String memberName;
    @NotBlank
    private String memberEmail;

}
