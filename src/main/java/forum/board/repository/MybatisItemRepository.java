package forum.board.repository;

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

    @Override
    public List<Item> findByWriter(String name) {
         return itemMapper.findByWriter(name);
    }

    @Override
    public List<Item> findByTitle(String title) {
        return itemMapper.findByTitle(title);
    }

    @Override
    public List<Item> findByKeyword(String keyword) {
        return itemMapper.findByKeyword(keyword);
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
