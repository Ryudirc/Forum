package forum.board.controller.DTO;


import forum.board.domain.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class ItemSaveForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<MultipartFile> attachFiles; //첨부파일들


}
