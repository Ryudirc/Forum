package forum.board.repository;

import forum.board.domain.Item;
import forum.board.domain.Member;

import java.util.List;

public interface boardRepository {

    void save(Item item);
    Item findById(Long itemId);
    List<Item> findByWriter(Long memberId);
    //List<Item> findByContent(String content);
    List<Item> findAll();
    Item delete(Long itemId);




}
