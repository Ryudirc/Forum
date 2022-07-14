package forum.board.repository;

import forum.board.domain.Item;
import forum.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class MemoryBoardRepository implements boardRepository{

    private final Map<Long,Item> itemRepository = new HashMap<>();
    private final memberRepository MemoryMemberRepository;
    private Long sequence = 0L;


    @Override
    public void save(Item item) {
        item.setItemId(++sequence);
        itemRepository.put(item.getItemId(),item);
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.get(itemId);
    }

   @Override
    public List<Item> findByWriter(Long memberId) {
        List<Item> items = new ArrayList<>();
        /*Member findMember = MemoryMemberRepository.findById(memberId);
        for (Item item : itemRepository.values()) {
            if(findMember.getMemberName().equals(item.getWriter()))
            {
                items.add(item);
            }
        }*/
        return items;
    }


    @Override
    public List<Item> findAll() {
        return itemRepository.values().stream().toList();
    }

    @Override
    public Item delete(Long itemId) {
      return itemRepository.remove(itemId);
    }
}
