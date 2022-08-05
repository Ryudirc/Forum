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


    private String title;
    private String content;
    private String writer;
    private int view_cnt; // 직접설정해주는 값이 아닌, 서버로직에 의해 증감되어 뷰로 전송될 값
    private int good_cnt; // 유저가 추천 버튼울 눌렀을때 서버로직에 의해 증감되어 뷰로 전송됨.
    private List<MultipartFile> attachFiles; //첨부파일들


}
