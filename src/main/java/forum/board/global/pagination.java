package forum.board.global;

import forum.board.domain.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 페이징을 위한 전용 클래스
 * - 쿼리는 Board 에서 가져오거나 혹은 Item 을 리스트로 불러온다.
 * - 페이징 컨트롤러를 통해 뷰로 데이터를 보낸다.
 */
@Component
@Getter
public class pagination {
    // 한 페이지에 보여지는 게시글 개수
    private int pageSize;

    // 한번에 보여질 버튼 개수(디폴트는 5개)
    private double btnCnt;

    //시작 페이지와 끝페이지
    private int start;
    private int end;

    private int btnBlockCnt;

    private int totalBtn;

    private List<Item> items;

    private int nowBlock;
    private int prevBlock;
    private int nextBlock;

    public pagination() {}

    public pagination(int start,int end,int pageSize, double btnCnt,int btnBlockCnt, int totalBtn, List<Item> items, int nowBlock, int prevBlock, int nextBlock) {

        this.start = start;
        this.end = end;
        this.pageSize = pageSize;
        this.btnCnt = btnCnt;
        this.btnBlockCnt = btnBlockCnt;
        this.totalBtn = totalBtn;
        this.items = items;
        this.nowBlock = nowBlock;
        this.prevBlock = prevBlock;
        this.nextBlock = nextBlock;
    }
}
