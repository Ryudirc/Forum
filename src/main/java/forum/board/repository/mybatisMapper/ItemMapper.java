package forum.board.repository.mybatisMapper;

import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemMapper {

    void save(Item item); // 게시글 작성
    Item findById(Long itemId); // 게시글 찾기

    List<Item> findAll(); // 게시판 입장시 처음 게시글을 뿌려주기위한 메서드
    List<Item> findByWriter(@Param("name") String name,@Param("startRow") int startRow, @Param("endRow") int endRow); // search 에 사용될 메서드( 작성자로 게시글 검색)
    List<Item> findByTitle(@Param("name") String title,@Param("startRow") int startRow, @Param("endRow") int endRow); // search 에 사용될 메서드 (제목으로 게시글 검색)
    List<Item> findByKeyword(@Param("keyword") String keyword,@Param("startRow") int startRow, @Param("endRow") int endRow); // search 에 사용될 메서드 (제목 + 게시글로 게시글 검색)
    void update(@Param("id") Long itemId, @Param("updateItem") Item updateItem); //itemId 로 게시글을 찾아서 updateItem 으로 업데이트
    void delete(Long itemId); // 게시글 삭제

    void updateViewCount(Long itemId); // 게시글 조회 시 조회수 1씩 증가

    // 좋아요(추천)기능 업데이트 메서드
    //void updateLike();


    //2022/08/02 추가 - 검색통한 게시글을 "모두" 조회해 오기위한 DB 쿼리 추가용 메서드
    List<Item> findByWriterAll(String name);

    List<Item> findByTitleAll(String title);

    List<Item> findByKeywordAll(String keyword);

}
