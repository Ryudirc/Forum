package forum.board.controller.DTO;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter @Setter
@Component
public class ItemForm {

    private Long itemId;
    private String title;
    private String content;
    private MultipartFile attachFile; //첨부파일
    private List<MultipartFile> attachFiles; //이미지 파일들
    private String writer;
    private Date date;

}
