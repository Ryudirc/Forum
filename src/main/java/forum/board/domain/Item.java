package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 매핑용 클래스
 */

@Getter @Setter
public class Item {
    private Long itemId;
    private String title;
    private String content;
    private String writer;
    private Date date;
    private Reply reply;
    private int viewCnt; //조회수
    private int goodCnt; //추천수

}
