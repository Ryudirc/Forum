package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 게시글 매핑용 클래스
 */

@Getter @Setter
public class Item {
    private Long num;
    private Long itemId;
    private String title;
    private String content;
    private String writer;
    private List<UploadFile> attachFiles;
    private int viewCnt;
    private Long writerId;



}
