package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 매핑용 클래스
 */

@Getter @Setter
public class Item {
    private Long itemId;
    private String title;
    private String content;
    private UploadFile attachFile; //첨부파일
    private List<UploadFile> imageFiles; //이미지 파일들
    private String writer;
    private int viewCnt;
    private int goodCnt;

}
