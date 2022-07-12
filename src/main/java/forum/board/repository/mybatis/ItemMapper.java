package forum.board.repository.mybatis;

import forum.board.domain.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(String itemId, Item UpdateItem);

    List<Item> findById(Long id); //회원 아이디로 조회

    List<Item> findByTitle(String title); //제목으로 조회

    List<Item> findByWriter(String writer); //작성자 이름으로 조회

}
