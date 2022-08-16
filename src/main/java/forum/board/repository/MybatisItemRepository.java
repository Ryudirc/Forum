package forum.board.repository;

import forum.board.controller.DTO.ItemUpdateForm;
import forum.board.domain.Item;
import forum.board.repository.RepositoryInterface.ItemRepository;
import forum.board.repository.mybatisMapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MybatisItemRepository implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public void save(Item item) {
        itemMapper.save(item);
    }

    @Override
    public Item findById(Long itemId) {
        return itemMapper.findById(itemId);
    }


    @Override
    public List<Item> findAll() {
        return itemMapper.findAll();
    }

    public List<Item> findByWriter(String name, int start, int pageSize) {
         return itemMapper.findByWriter(name,start,pageSize);
    }

    public List<Item> findByTitle(String title, int start, int pageSize) {
        return itemMapper.findByTitle(title,start,pageSize);
    }

    public List<Item> findByKeyword(String keyword, int start, int pageSize) {
        return itemMapper.findByKeyword(keyword,start,pageSize);
    }

    public List<Item> findByWriterAll(String name) {
        return itemMapper.findByWriterAll(name);
    }

    public List<Item> findByTitleAll(String title) {
        return itemMapper.findByTitleAll(title);
    }

    public List<Item> findByKeywordAll(String keyword) {
        return itemMapper.findByKeywordAll(keyword);
    }




    @Override
    public void update(Long itemId, Item updateItem) {
        itemMapper.update(itemId,updateItem);
    }

    @Override
    public void delete(Long itemId) {
        itemMapper.delete(itemId);
    }

    @Override
    public void updateViewCount(Long itemId) {
        itemMapper.updateViewCount(itemId);
    }


}
