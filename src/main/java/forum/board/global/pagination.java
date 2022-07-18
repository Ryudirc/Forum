package forum.board.global;

import org.springframework.stereotype.Component;

/**
 * 페이징을 위한 전용 클래스
 * - 쿼리는 Board 에서 가져오거나 혹은 Item 을 리스트로 불러온다.
 * - 페이징 컨트롤러를 통해 뷰로 데이터를 보낸다.
 */
@Component
public class pagination {
    // 한 페이지에 보여지는 게시글 개수
    private int pageSize = 15;

    // 페이징 버튼 당 보여줄 게시글 개수
    private int blockSize;



}
