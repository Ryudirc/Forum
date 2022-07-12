package forum.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Board {
    private Long boardId; // itemId 와 PK-FK 관계
    private String title;
    private String content;
    private Member member;
    private Reply reply;
    private int viewCnt; //조회수
    private int goodCnt; //추천수

}
