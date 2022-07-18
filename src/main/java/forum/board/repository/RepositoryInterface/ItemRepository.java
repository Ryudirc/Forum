package forum.board.repository.RepositoryInterface;

import forum.board.domain.Item;

import java.util.List;

public interface ItemRepository {

    void save(Item item); // 게시글 작성
    Item findById(Long itemId); // 게시글 찾기( 서버 내부에서 사용될 메서드?)
    List<Item> findAll();
    List<Item> findByWriter(String name); // search 에 사용될 메서드( 작성자로 게시글 검색)
    List<Item> findByTitle(String title); // search 에 사용될 메서드 (제목으로 게시글 검색)
    List<Item> findByKeyword(String keyword); // search 에 사용될 메서드 (제목 + 게시글 검색)
    void update(Long itemId, Item updateItem); //itemId 로 게시글을 찾아서 updateItem 으로 업데이트
    void delete(Long itemId); // 게시글 삭제

    void updateViewCount(Long itemId); // 게시글 조회 시 카운트 +1




}
