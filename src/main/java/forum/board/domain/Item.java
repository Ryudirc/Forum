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
    private Long num;
    private Long itemId;
    private String title;
    private String content;
    private String writer;
    private int viewCnt;
    private int goodCnt;



}
